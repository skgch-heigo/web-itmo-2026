package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class ArticleRepositoryImpl implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();


    @Override
    public Article find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Article` WHERE `id`=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toArticle(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
    }

    @Override
    public List<Article> findAllByUserId(long id) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM `Article` WHERE `userId`=? ORDER BY `creationTime` DESC")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find articles.", e);
        }
        return articles;
    }

    @Override
    public List<Article> findAll(boolean hideHidden) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM `Article` ORDER BY `creationTime` DESC";
        if (hideHidden) {
            query = "SELECT * FROM `Article` WHERE `hidden`=FALSE ORDER BY `creationTime` DESC";
        }
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find articles.", e);
        }
        return articles;
    }


    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }

    @Override
    public void save(Article article) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `Article` (`userId`, `title`, `text`, `hidden`, `creationTime`) VALUES (?, ?, ?, ?,  NOW())",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setLong(1, article.getUserId());
                statement.setString(2, article.getTitle());
                statement.setString(3, article.getText());
                statement.setBoolean(4, article.getHidden());
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save article.");
                } else {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            article.setId(generatedKeys.getLong(1));
                            article.setCreationTime(find(article.getId()).getCreationTime());
                        } else {
                            throw new RepositoryException("Can't save article [no autogenerated fields].");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save article.", e);
        }
    }

    @Override
    public void setHidden(long id, boolean newHidden) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `Article` SET `hidden`=? WHERE `id`=?")) {
                statement.setBoolean(1, newHidden);
                statement.setLong(2, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't update article", e);
        }
    }
}

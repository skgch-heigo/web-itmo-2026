package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.RED),
            new User(6, "pashka", "Pavel Mavrin", User.Color.RED),
            new User(9, "geranazavr555", "Georgiy Nazarov", User.Color.RED),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Keyboard Smash", "tjtykhuuytresdfxcvhbjkhuythregsdfhgjkuytdrsdvzfxcfhgjftdrfsvdzxvcbvjfhgdfsvdzxvcvghmghftdrhtersfdbxhvytdrhgfchvjgyftjhdrsfdxchgjgyuftjdrhgcvhjguytfrtcyrdcrdxtesrwqaserdxrycftuvybujhnoinbvtfcrydxyedsxyrcjhvbhjbhgbvtfvtufctrdycyrdcxrdcrcurtdcrdcxrycxycxrdcxyrxerxertxyxyrxcrxyrccutviybyibvtufcrcrycxrxyrxrexetrxycyrutuvtvbiyvtuftucrycxrexrcrtctuiyvturycdextsedrcftvyvuftcydrextszrwesxdcrvfgybgytfucdrexetxrctvybgivftcryextswsrexrctvty", 1),
            new Post(2, "Something Important", "Something important", 11),
            new Post(3, "Meow meow", "Meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow", 6)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}

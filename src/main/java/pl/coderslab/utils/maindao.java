package pl.coderslab.utils;

public class maindao {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User secondUser = new User();
        secondUser.setUsername("marek");
        secondUser.setEmail("marek@coderslab.pl");
        secondUser.setPassword("pass");
        userDAO.create(secondUser);
        User[] all = userDAO.findAll();
        for (User u : all) {
            System.out.println(u);
    }

    }
}

package com.desktopapp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.desktopapp.model.UserData;

import java.util.List;

public class Authentification
{
  private UserData user = null;
  private Boolean userExists = false;

  public UserData getUser() {
    return this.user;
  }

  public Boolean userExists() {
    return this.userExists;
  }

  public static Authentification tryLogin(String user, String pass) {
    Authentification auth = new Authentification();

    // Cria uma sessão
    Session session = HibernateUtil
      .getSessionFactory()
      .getCurrentSession();
    Transaction transaction = session.beginTransaction();
    
    Query query = session.createQuery(
      "from UserData u where u.username = :user"
    );
    query.setParameter("user", user);
    List<UserData> users = query.list();

    transaction.commit();
    if (users.size() == 0)
      return auth;

    auth.userExists = true;
    UserData loggedUser = users.get(0);
    if (!loggedUser.getUserpass().equals(pass))
      return auth;

    auth.user = loggedUser;
    return auth;
  }
}
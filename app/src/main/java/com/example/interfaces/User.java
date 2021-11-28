package com.example.interfaces;

public interface User {

    /**
     * @return  return the user name
     */
    public String getName();

    /**
     * @param name - user name
     * @return
     */
    public void setName(String name);

    /**
     * @return  return the user last name
     */
    public String getLastname();

    /**
     * @param lastname - user last name
     * @return
     */
    public void setLastname(String lastname) ;

    /**
     * @return  return the user mail
     */
    public String getEmail();

    /**
     * @param email - user mail
     * @return
     */
    public void setEmail(String email);

    /**
     * @return  return the user location
     */
    public String getArea();

    /**
     * @param area - user location
     * @return
     */
    public void setArea(String area);

    /**
     * @return  return the user number
     */
    public String getNumber();

    /**
     * @param number - user number
     * @return
     */
    public void setNumber(String number);

    /**
     * @return  return the user type (angle/ traveler)
     */
    public String getType();


}

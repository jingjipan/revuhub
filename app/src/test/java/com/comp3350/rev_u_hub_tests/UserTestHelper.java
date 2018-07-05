package com.comp3350.rev_u_hub_tests;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public abstract class UserTestHelper extends UnitTestHelper {

    public static final String[] TEST_DESCRIPTORS = {"a random user",
            "a random user with numbers and symbols",
            "no user",
            "a random user with zero length contents",
            "a random user with short contents",
            "a random user with long contents"};

    public static UserObject randomUser() {
        return randomUserObject();
    }

    // allChars = all characters, not just words
    public static UserObject randomUser(boolean allChars) {
        return randomUserObject(allChars);
    }

    public static UserObject randomUser(int length) {
        return randomUserObject(length);
    }

    // allChars = all characters, not just words
    public static UserObject randomUser(boolean allChars, int length) {
        return randomUserObject(allChars, length);
    }

    public static UserObject[] getUserTestArray() {
        return new UserObject[]{
                UserTestHelper.randomUser(false),
                UserTestHelper.randomUser(true),
                new UserObject(),
                UserTestHelper.randomUser(true,0),
                UserTestHelper.randomUser(true,1),
                UserTestHelper.randomUser(true,168)
        };
    }

    public static UserObject randomUserObject() {
        return randomUserObject(false, UserTestHelper.randomInteger());
    }

    public static UserObject randomUserObject(boolean allChars) {
        return randomUserObject(allChars, UserTestHelper.randomInteger());
    }

    public static UserObject randomUserObject(int length) {
        return randomUserObject(false,length);
    }

    // allChars = all characters, not just words
    public static UserObject randomUserObject(boolean allChars, int length) {
        UserObject randUser;
        if (allChars) {
            randUser = new UserObject(
                    randomString(length),
                    randomString(length)
            );
        } else {
            randUser = new UserObject(
                    randomWord(length),
                    randomWord(length)
            );
        }
        return randUser!=null && !randUser.isEmpty() ? randUser : new UserObject();
    }
}
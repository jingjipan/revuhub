package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

public abstract class InfoFormatConverter {
    public static String convertToString(Object target) {
        if (target instanceof String) return convertToString((String) target);
        if (target instanceof List) return convertToString((List<String>) target);
        if (target instanceof ReviewDMObject) return convertToString((ReviewDMObject) target);
        if (target instanceof File) return convertToString((File) target);
        return target.toString();
    }

    public static List<String> convertToStringList(Object target) {
        if (target instanceof String) return convertToStringList((String) target);
        if (target instanceof List) return convertToStringList((List<String>) target);
        if (target instanceof ReviewDMObject) return convertToStringList((ReviewDMObject) target);
        if (target instanceof File) return convertToStringList((File) target);
        return convertToStringList(target.toString());
    }

    public static ReviewDMObject convertToReviewDMObject(Object target) {
        if (target instanceof String) return convertToReviewDMObject((String) target);
        if (target instanceof List) return convertToReviewDMObject((List<String>) target);
        if (target instanceof ReviewDMObject) return convertToReviewDMObject((ReviewDMObject) target);
        if (target instanceof File) return convertToReviewDMObject((File) target);
        return convertToReviewDMObject(target.toString());
    }

    public static File convertToFile(Object target) {
        if (target instanceof String) return convertToFile((String) target);
        if (target instanceof List) return convertToFile((List<String>) target);
        if (target instanceof ReviewDMObject) return convertToFile((ReviewDMObject) target);
        if (target instanceof File) return convertToFile((File) target);
        return convertToFile(target.toString());
    }

    private static String convertToString(String target) {
        return target;
    }

    private static String convertToString(List<String> target) {
        return buildString(target.toArray(),", ");
    }

    private static String convertToString(ReviewDMObject target) {
        return convertToString(target.getReviews());
    }

    private static String convertToString(File target) {
        return target.getPath();
    }

    private static List<String> convertToStringList(String target) {
        return new ArrayList<>(Arrays.asList(target.split(", ")));
    }

    private static List<String> convertToStringList(List<String> target) {
        return new ArrayList<>(target);
    }

    private static List<String> convertToStringList(ReviewDMObject target) {
        return target.getReviews();
    }

    private static List<String> convertToStringList (File target) {
        return new ArrayList<>(Arrays.asList(target.getPath().split("\\\\")));
    }

    private static ReviewDMObject convertToReviewDMObject(String target) {
        return new ReviewDMObject(target);
    }

    private static ReviewDMObject convertToReviewDMObject(List<String> target) {
        return new ReviewDMObject(target);
    }

    private static ReviewDMObject convertToReviewDMObject(ReviewDMObject target) {
        return new ReviewDMObject(target);
    }

    private static ReviewDMObject convertToReviewDMObject(File target) {
        return new ReviewDMObject(convertToStringList(target));
    }

    private static File convertToFile(String target) {
        return new File(target);
    }

    private static File convertToFile(List<String> target) {
        return new File(buildString(target.toArray(),"\\\\"));
    }

    private static File convertToFile(ReviewDMObject target) {
        return convertToFile(target.getReviews());
    }

    private static File convertToFile(File target) {
        return new File(target.getPath());
    }

    private static String buildString(Object[] substrings, String delim) {
        String output = (String) substrings[0];
        for (int i=1; i<substrings.length; i++) {
            output += delim + substrings[i];
        }
        return output;
    }
}
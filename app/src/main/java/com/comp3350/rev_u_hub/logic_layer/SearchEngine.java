package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.SearchableObject;

public abstract class SearchEngine  {

    private static final String lowercaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final String uppercaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "0123456789";
    private static final String symbols = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    // An attempt to access some persistent object using a string
    protected abstract SearchableObject fetchPersistent(String searchText);

    // What the search should return if it fails
    protected abstract SearchableObject defaultObject();

    SearchableObject getObjectSimple(String title) {
        return sanitizeSearchable(fetchPersistent(title));
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a searchText to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    SearchableObject getObject(String searchText) {
        SearchableObject objectFound;

        objectFound = deletionSearch(searchText);
        if (isEmpty(objectFound)) objectFound = transpositionSearch(searchText);

        if (isEmpty(objectFound))
            objectFound = insertionSearch(searchText, lowercaseAlphabet);
        if (isEmpty(objectFound))
            objectFound = substitutionSearch(searchText, lowercaseAlphabet);
        if (isEmpty(objectFound))
            objectFound = insertionSearch(searchText, uppercaseAlphabet);
        if (isEmpty(objectFound))
            objectFound = substitutionSearch(searchText, uppercaseAlphabet);
        if (isEmpty(objectFound))
            objectFound = insertionSearch(searchText, numbers);
        if (isEmpty(objectFound))
            objectFound = substitutionSearch(searchText, numbers);
        if (isEmpty(objectFound))
            objectFound = insertionSearch(searchText, symbols);
        if (isEmpty(objectFound))
            objectFound = substitutionSearch(searchText, symbols);

        return sanitizeSearchable(objectFound);
    }

    private boolean isEmpty(SearchableObject theObject) {
        return theObject==null || theObject.isEmpty();
    }

    private SearchableObject sanitizeSearchable(SearchableObject theObject) {
        if (theObject==null) return defaultObject();
        else if (theObject.isEmpty()) return defaultObject();
        else return theObject;
    }

    // removing one character in the searchText
    private SearchableObject deletionSearch(String searchText) {
        SearchableObject objectFound = null;
        String attempt;

        for (int i=0; i<searchText.length() && isEmpty(objectFound); i++) {
            attempt = searchText.substring(0,i) + searchText.substring(i+1,searchText.length());
            objectFound = fetchPersistent(attempt);
        }

        return objectFound;
    }

    // swapping two adjacent characters in the searchText
    private SearchableObject transpositionSearch(String searchText) {
        SearchableObject objectFound = null;
        String attempt;

        for (int i=0; i<searchText.length()-1 && isEmpty(objectFound); i++) {
            attempt = searchText.substring(0,i) +
                    searchText.substring(i+1,i+2) + searchText.substring(i,i+1) +
                    searchText.substring(i+2,searchText.length());
            objectFound = fetchPersistent(attempt);
        }

        return objectFound;
    }

    // inserting one character somewhere in the searchText
    private SearchableObject insertionSearch(String searchText, String validChars) {
        SearchableObject objectFound = null;
        String attempt;

        for (int i=0; i<=searchText.length() && isEmpty(objectFound); i++) {

            for (int j=0; j<validChars.length() && isEmpty(objectFound); j++) {
                attempt = searchText.substring(0, i) +
                        validChars.charAt(j) +
                        searchText.substring(i, searchText.length());
                objectFound = fetchPersistent(attempt);
            }
        }

        return objectFound;
    }

    // changing one character in the searchText
    private SearchableObject substitutionSearch(String searchText, String validChars) {
        SearchableObject objectFound = null;
        String attempt;

        for (int i=0; i<searchText.length() && isEmpty(objectFound); i++) {

            for (int j=0; j<validChars.length() && isEmpty(objectFound); j++) {
                attempt = searchText.substring(0, i) +
                        validChars.charAt(j) +
                        searchText.substring(i+1, searchText.length());
                objectFound = fetchPersistent(attempt);
            }
        }

        return objectFound;
    }
}
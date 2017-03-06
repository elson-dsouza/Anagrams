package com.google.engedu.anagrams;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    private HashMap<String,ArrayList> lettersToWords;

    @NonNull
    private String ssort(String w){
        char wordarr[]=w.toCharArray();
        Arrays.sort(wordarr);
        return Arrays.toString(wordarr);
    }

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet=new HashSet<>();
        wordList=new ArrayList<>();
        lettersToWords =new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String t=ssort(word);

            if(lettersToWords.containsKey(t)){
                ArrayList<String> temp=lettersToWords.get(t);
                temp.add(word);
            }
            else{
                ArrayList<String> temp=new ArrayList<>();
                temp.add(word);
                lettersToWords.put(t,temp);
            }
        }
    }


    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word)&&!word.contains(base))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char t='a';t<='z';t++){
            String temp=word.concat(Character.toString(t));
            temp=ssort(temp);
            if(lettersToWords.containsKey(temp))
                result.addAll(lettersToWords.get(temp));
        }
        return result;
    }

    public String pickGoodStarterWord() {
            while (true) {
                int i = random.nextInt() % wordList.size();
                for (; i < wordList.size()&&i>=0; i++) {
                    String temp = wordList.get(i);
                    String t = ssort(temp);

                    if (lettersToWords.containsKey(t)) {
                        if ((lettersToWords.get(t)).size() >= MIN_NUM_ANAGRAMS) {
                            return temp;
                        }
                    }
                }
            }
    }
}

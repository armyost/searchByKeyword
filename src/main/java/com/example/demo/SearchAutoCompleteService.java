package com.example.demo;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchAutoCompleteService {
    private static final Logger logger = LoggerFactory.getLogger(SearchAutoCompleteService.class);

    private final SearchWordRepository searchWordRepository;

    private String[] cons = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};
    private String[] starter = {"가", "까", "나", "다", "따", "라", "마", "바", "빠", "사", "싸", "아", "자", "짜", "차", "카", "타", "파", "하", "힣"};
    

    public String getConsonants(String searchText) {
        String[] cons = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

        if (searchText.length() > 0) {
            // logger.info("!!! chName is : {} !!!", searchText.charAt(0));
            char chName = searchText.charAt(0);
            if (chName >= 0xAC00) {
                int uniVal = chName - 0xAC00;
                int cho = ((uniVal - (uniVal % 28)) / 28) / 21;

                // logger.info("!!! Consonant is : {} !!!", cons[cho]);
                return cons[cho];
            }
        }

        return "해당하는 값이 없습니다.";
    }

    public ArrayList<String> getAutoComplete(String searchText) {
        ArrayList<String> resultArrList = new ArrayList<>();
        List<String> searchResultList = new ArrayList<>();
        char lastChar = searchText.charAt(searchText.length()-1);
        String lastString = Character.toString(lastChar);

        if (Arrays.asList(this.cons).contains(lastString)){
            // logger.info("!!! Search with cons !!!");
            String searchWordBegin = makeSearchWordBeginString(searchText, lastString);
            String searchWordEnd = makeSearchWordEndString(searchText, lastString);
            searchResultList = searchWordRepository.findByWordCons(searchWordBegin, searchWordEnd);
        } else {
            // logger.info("!!! Search with word !!!");
            searchResultList = searchWordRepository.findByWord(searchText);
        }

        // logger.info("!!! Search Result is : {} !!!", searchResultList);
        for (String value : searchResultList){
            resultArrList.add(value);
        }
        return resultArrList;
    }

    public String makeSearchWordBeginString (String searchText, String lastString){
        String result = "EMPTY_VALUE";
        int index = Arrays.asList(this.cons).indexOf(lastString);
        String tempString = this.starter[index];
        result = searchText.substring(0, searchText.length()-1);
        result += tempString;
        // logger.info("!!! makeSearchWordBeginString is : {} !!!", result);
        return result;
    }

    public String makeSearchWordEndString (String searchText, String lastString){
        String result = "EMPTY_VALUE";
        int index = Arrays.asList(this.cons).indexOf(lastString);
        searchText.charAt(searchText.length()-1);
        String tempString = this.starter[index+1];
        result = searchText.substring(0, searchText.length()-1);
        result += tempString;
        // logger.info("!!! makeSearchWordEndString is : {} !!!", result);
        return result;
    }
}

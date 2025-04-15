package org.example;

import org.example.constants.DelimiterConstants;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.enums.WordScreenType;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.files.FileUtils;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        // w-frage
//        List<String> values = GenericRepo.getAllFromFileAsList(GermanFilePathConstants.GERMAN_W_FRAGE_FOLDER_PATH + File.separator + "w-frage");

        // noun
//        List<String> values = GenericRepo.getAllFromFileAsList(GermanFilePathConstants.GERMAN_NOUN_FOLDER_PATH + File.separator + "random_noun");

        //verb
//        List<String> values = GenericRepo.getAllFromFileAsList(GermanFilePathConstants.GERMAN_VERB_FOLDER_PATH + File.separator + "random_verb");
//
//        values.forEach(e -> {
//            try {
//                String value = e.split(DelimiterConstants.regexPipSeperator)[0].trim();
//
//                byte[] audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), value);
////                FileUtils.saveBytesAsFile(audioData, WordScreenType.GERMAN_W_FRAGE.getAudioPath() + File.separator + value.toLowerCase() + ".mp3");
////                FileUtils.saveBytesAsFile(audioData, WordScreenType.GERMAN_NOUN.getAudioPath() + File.separator + value.toLowerCase() + ".mp3");
//                FileUtils.saveBytesAsFile(audioData, WordScreenType.GERMAN_VERB.getAudioPath() + File.separator + value.toLowerCase() + ".mp3");
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//
//
//        });

        MainFrame mainFrame = new MainFrame(730, 1000);


    }
}


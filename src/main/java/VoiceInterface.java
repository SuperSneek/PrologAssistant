import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.*;
import java.util.Locale;

public class VoiceInterface {

    private final PrologConnector prologConnector = new PrologConnector();

    public static void main(String[] args) {

        //TODO: Place in seperate thread
        VoiceInterface voiceInterface = new VoiceInterface();

        voiceInterface.ReadSpeech();

    }

    private void ReadSpeech() {
        Configuration config = new Configuration();

        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        //LiveSpeechRecognizer recognizer = null;
        //try {
        //    recognizer = new LiveSpeechRecognizer(config);
        //} catch (IOException e) {
        //    return;
        //}
        //recognizer.startRecognition(true);
        //SpeechResult result;
        //while ((result = recognizer.getResult()) != null) {
        //    System.out.format("Hypothesis: %s\n", result.getHypothesis());
        //    String[] words = splitPhrase(result.getHypothesis());
        //    if(words[0].toUpperCase().equals("COMPUTER")) {
        //       // prologConnector.ProcessCommand(words);
        //    }
        //}
        //recognizer.stopRecognition();
    }

    private static String[] splitPhrase(String phrase) {
        return phrase.split(" ");
    }

}

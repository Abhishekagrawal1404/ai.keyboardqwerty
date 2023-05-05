import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import ai.openai.OpenAI;
import ai.openai.OpenAICompletion;
import ai.openai.OpenAIAPIException;
import ai.openai.giphy.Giphy;
import ai.openai.giphy.GiphyAPIException;
import ai.openai.speech.SpeechRecognizer;

public class AIKeyboard implements ActionListener {

    private static final String OPENAI_API_KEY = "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";
    private static final String GIPHY_API_KEY = "1JQfNloEq2bj37n0kzkhiO84tRUnAgx9";
    private static final String MODEL_ENGINE = "davinci";
    
    private OpenAI openai;
    private Giphy giphy;
    private SpeechRecognizer speechRecognizer;
    
    private JFrame frame;
    private JPanel panel;
    private JTextField textEntry;
    private JButton sendButton;
    private JButton autocorrectButton;
    private JButton generateTextButton;
    private JButton generateGifButton;
    private JButton translateButton;
    private JButton voiceToTextButton;
    private JButton predictiveTextButton;
    private JButton messagingButton;
    private JButton socialMediaButton;
    
    public AIKeyboard() {
        openai = new OpenAI("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", "davinci");
        giphy = new Giphy("JQfNloEq2bj37n0kzkhiO84tRUnAgx9";
        speechRecognizer = new SpeechRecognizer();
        
        frame = new JFrame("ORANET AI Keyboard");
        panel = new JPanel();
        textEntry = new JTextField(50);
        sendButton = new JButton("Send");
        autocorrectButton = new JButton("Autocorrect");
        generateTextButton = new JButton("Generate Text");
        generateGifButton = new JButton("Generate Gif");
        translateButton = new JButton("Translate");
        voiceToTextButton = new JButton("Voice to Text");
        predictiveTextButton = new JButton("Predictive Text");
        messagingButton = new JButton("Messaging Platform");
        socialMediaButton = new JButton("Social Media Platform");
        
        sendButton.addActionListener(this);
        autocorrectButton.addActionListener(this);
        generateTextButton.addActionListener(this);
        generateGifButton.addActionListener(this);
        translateButton.addActionListener(this);
        voiceToTextButton.addActionListener(this);
        predictiveTextButton.addActionListener(this);
        messagingButton.addActionListener(this);
        socialMediaButton.addActionListener(this);
        
        panel.add(textEntry);
        panel.add(sendButton);
        panel.add(autocorrectButton);
        panel.add(generateTextButton);
        panel.add(generateGifButton);
        panel.add(translateButton);
        panel.add(voiceToTextButton);
        panel.add(predictiveTextButton);
        panel.add(messagingButton);
        panel.add(socialMediaButton);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == sendButton) {
        sendMessage();
    } else if (e.getSource() == autocorrectButton) {
        autocorrect();
    } else if (e.getSource() == generateTextButton) {
        generateText();
    } else if (e.getSource() == generateGifButton) {
        generateGif();
    } else if (e.getSource() == translateButton) {
        translate();
    } else if (e.getSource() == voiceToTextButton) {
        voiceToText();
    } else if (e.getSource() == predictiveTextButton) {
        predictiveText();
    } else if (e.getSource() == messagingButton) {
        messagingPlatform();
    } else if (e.getSource() == socialMediaButton) {
        socialMediaPlatform();
    }
}

public void sendMessage() {
    String message = textEntry.getText();
    // TODO: send the message to the desired platform
    textEntry.setText("");
}

public void autocorrect() {
    String message = textEntry.getText();
    try {
        String correctedMessage = OpenAIUtils.autocorrect(message, openai);
        textEntry.setText(correctedMessage);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void generateText() {
    String prompt = textEntry.getText();
    String modelId = "text-davinci-002";
    String response = openai.generateText(prompt, modelId);
    textEntry.setText(response);
}

public void generateGif() {
    String searchQuery = textEntry.getText();
    String gifUrl = giphy.getRandomGifUrl(searchQuery);
    textEntry.setText(gifUrl);
}

public void translate() {
    String textToTranslate = textEntry.getText();
    String translatedText = translator.translate(textToTranslate, "es");
    textEntry.setText(translatedText);
}

public void voiceToText() {
    String spokenText = speechRecognizer.recognizeSpeech();
    textEntry.setText(spokenText);
}

public void predictiveText() {
    String message = textEntry.getText();
    String modelId = "text-davinci-002";
    String prompt = message + "\n";
    ArrayList<String> completions = openai.generateCompletions(prompt, modelId);
    if (completions.size() > 0) {
        String prediction = completions.get(0);
        textEntry.setText(prediction);
    }
}

public void messagingPlatform() {
    // TODO: integrate with the desired messaging platform API
}

public void socialMediaPlatform() {
    // TODO: integrate with the desired social media platform API
}

public static void main(String[] args) {
    new AIKeyboard();
}
class OpenAIUtils {
public static String autocorrect(String input, OpenAI openai) throws IOException {
String modelId = "text-davinci-002";
String prompt = "Please correct the following sentence: " + input + "\nCorrected sentence:";
String response = openai.generateText(prompt, modelId);
String[] lines = response.split("\n");
String output = lines[lines.length - 1];
return output.trim();
}
}

class OpenAI {
private final String apiKey;
private final String modelEngine;
public OpenAI(String apiKey, String modelEngine) {
    this.apiKey = apiKey;
    this.modelEngine = modelEngine;
}

public String generateText(String prompt) throws IOException {
    return generateText(prompt, null, null);
}

public String generateText(String prompt, String modelId) throws IOException {
    return generateText(prompt, modelId, null);
}

public String generateText(String prompt, String modelId, Integer maxTokens) throws IOException {
    String url = "https://api.openai.com/v1/engines/" + modelEngine + "/completions";
    String parameters = "prompt=" + prompt;
    if (modelId != null) {
        parameters += "&model=" + modelId;
    }
    if (maxTokens != null) {
        parameters += "&max_tokens=" + maxTokens;
    }
    String response = HttpUtils.post(url, parameters, apiKey);
    JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
    String text = obj.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
    return text;
}

public ArrayList<String> getPredictions(String prompt) throws IOException {
    return getPredictions(prompt, null, null);
}

public ArrayList<String> getPredictions(String prompt, String modelId) throws IOException {
    return getPredictions(prompt, modelId, null);
}

public ArrayList<String> getPredictions(String prompt, String modelId, Integer maxTokens) throws IOException {
    String url = "https://api.openai.com/v1/engines/" + modelEngine + "/completions";
    String parameters = "prompt=" + prompt;
    if (modelId != null) {
        parameters += "&model=" + modelId;
    }
    if (maxTokens != null) {
        parameters += "&max_tokens=" + maxTokens;
    }
    String response = HttpUtils.post(url, parameters, apiKey);
    JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
    JsonArray choices = obj.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonArray("text");
    ArrayList<String> predictions = new ArrayList<String>();
    for (int i = 0; i < choices.size(); i++)
{
String prediction = choices.get(i).getAsString();
predictions.add(prediction);
}
return predictions;
}

public String getAnswer(String question) throws IOException {
return getAnswer(question, null, null);
}

public String getAnswer(String question, String context) throws IOException {
return getAnswer(question, null, context);
}

public String getAnswer(String question, String modelId, String context) throws IOException {
String prompt = "Question: " + question + "\nContext: " + context + "\nAnswer:";
String response = generateText(prompt, modelId);
return response.trim();
}

public ArrayList<String> getAnswers(String question, ArrayList<String> contexts) throws IOException {
return getAnswers(question, null, contexts);
}

public ArrayList<String> getAnswers(String question, String modelId, ArrayList<String> contexts) throws IOException {
ArrayList<String> answers = new ArrayList<String>();
for (String context : contexts) {
String answer = getAnswer(question, modelId, context);
answers.add(answer);
}
return answers;
}
}

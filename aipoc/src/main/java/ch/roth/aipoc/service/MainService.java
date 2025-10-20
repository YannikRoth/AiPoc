package ch.roth.aipoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

    private final OpenAiChatModel openAiChatModel;

    public String greeting(String name) {

        String promptText = "Write a short greeting message for a person named " + name + ".";
        Prompt namePrompt = Prompt.builder().content(promptText).build();

        ChatResponse chatResponse = openAiChatModel.call(namePrompt);
        return chatResponse.getResult().getOutput().getText();

    }

}

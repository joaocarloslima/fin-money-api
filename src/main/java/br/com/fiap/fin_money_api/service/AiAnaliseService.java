package br.com.fiap.fin_money_api.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.fin_money_api.model.TransactionType;
import br.com.fiap.fin_money_api.repository.TransactionRepository;

@Service
public class AiAnaliseService {

    private ChatClient chatClient;
    private TransactionRepository transactionRepository;

    public AiAnaliseService(ChatClient.Builder chatClientBuilder, TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
        this.chatClient = chatClientBuilder
                                .defaultSystem("Responda sempre em {lang}. Quando fizer uma analise, seja objetivo e responda em no máximo 80 caracteres.")
                                .defaultOptions(ChatOptions.builder().temperature(0.5).topP(1.0).build())
                                .build();
    }

    public String getExpenseAnalise(String lang){
        var expenses = transactionRepository.findByType(TransactionType.EXPENSE);

        var objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(expenses);

            return chatClient
                .prompt()
                .user("faça uma análise das minhas despesas: " + json) //RAG
                .system(sp -> sp.param("lang", lang))
                .call()
                .content();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "erro ao converter para json";
        
    }

    public String getIncomeAnalise(){
        return chatClient
            .prompt()
            .user("analise minhas receitas")
            .call()
            .content();
    }
    
}

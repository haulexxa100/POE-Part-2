/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepart2;


    
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class QuickChatApp {
    private final ArrayList<Message> messages = new ArrayList<>();
    private int messageNum = 0;
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        QuickChatApp app = new QuickChatApp();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to QuickChat.");
        System.out.print("Username: ");
        scanner.nextLine();
        System.out.print("Password: ");
        scanner.nextLine(); 
        System.out.println("Login successful.\n");

        System.out.print("How many messages would you like to send? ");
        int maxMessages = Integer.parseInt(scanner.nextLine());

        OUTER:
        while (true) {
            System.out.println("\n1) Send Message");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    if (messages.size() >= maxMessages) {
                        System.out.println("Message limit reached.");
                        continue;
                    }   System.out.print("Enter recipient number (with international code): ");
                    String recipient = scanner.nextLine();
                    if (!recipient.startsWith("+") || recipient.length() > 13) {
                        System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        continue;
                    }   System.out.print("Enter your message (max 250 characters): ");
                    String content = scanner.nextLine();
                    if (content.length() > 250) {
                        System.out.printf("Message exceeds 250 characters by %d, please reduce size.\n", content.length() - 250);
                        continue;
                    }
                     else if (content.length() > 50) {
                        System.out.println("Please enter a message of less than 50 characters.");
                        continue;
                    } else {
                        System.out.println("Message ready to send.");
                    }   String messageID = generateMessageID();
                    Message msg = new Message(messageID, messageNum, recipient, content);
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose an action: ");
                    String action = scanner.nextLine();
                    String result = msg.sentMessage(action);
                switch (action) {
                    case "1" -> {
                        messages.add(msg);
                        messageNum++;
                        System.out.println(result);
                        System.out.println(msg.printMessage());
                    }
                    case "2" -> System.out.println(result);
                    case "3" -> System.out.println("Message stored locally (not implemented yet).");
                    default -> System.out.println("Invalid action.");
                }
                }
                case "2" -> System.out.println("Coming Soon.");
                case "3" -> {
                    System.out.println("Total messages sent: " + messageNum);
                    break OUTER;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}

final class Message {
    private final String messageID;
    private final int messageNum;
    private final String recipient;
    private final String content;
    private final String messageHash;

    public Message(String messageID, int messageNum, String recipient, String content) {
        this.messageID = messageID;
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = content.split(" ");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
        return firstTwo + ":" + messageNum + ":" + firstWord + lastWord;
    }

    public String sentMessage(String action) {return switch (action) {
            case "1" -> "Message successfully sent.";
            case "2" -> "Press 0 to delete message.";
            case "3" -> "Message successfully stored.";
            default -> "Invalid option.";
        };
    }

    public String printMessage() {
        return "MessageID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + content;
    }
}


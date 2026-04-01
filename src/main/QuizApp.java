package main;

import java.io.*;
import java.util.*;
import model.Question;
import service.QuizService;

public class QuizApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuizService service = new QuizService();

        int score = 0;

        System.out.println("===== ONLINE QUIZ SYSTEM =====");

        // Take user name
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        List<Question> questions = service.getQuestions();

        for (Question q : questions) {
            System.out.println("\n" + q.getQuestion());

            String[] options = q.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            System.out.println("⏱ You have 10 seconds to answer...");
            long startTime = System.currentTimeMillis();

            System.out.print("Enter answer: ");
            int ans = sc.nextInt();

            long endTime = System.currentTimeMillis();

            if ((endTime - startTime) > 10000) {
                System.out.println("⏰ Time's up!");
            } else if (ans == q.getCorrectAnswer()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong!");
            }
        }

        // Final Result
        System.out.println("\n🎯 Final Score: " + score + "/" + questions.size());

        if (score == questions.size()) {
            System.out.println("🏆 Excellent!");
        } else if (score >= 3) {
            System.out.println("👍 Good job!");
        } else {
            System.out.println("😅 Try again!");
        }

        // Save result to file
        try {
            FileWriter fw = new FileWriter("results.txt", true);
            fw.write("Name: " + name + " | Score: " + score + "/" + questions.size() + "\n");
            fw.close();

            System.out.println("💾 Result saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }

        // ================= LEADERBOARD =================
        System.out.println("\n🏆 LEADERBOARD");

        class Result {
            String name;
            int score;

            Result(String name, int score) {
                this.name = name;
                this.score = score;
            }
        }

        List<Result> results = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("results.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                // Format: Name: Madhu | Score: 4/5
                String[] parts = line.split("\\|");

                String userName = parts[0].split(":")[1].trim();
                String scorePart = parts[1].split(":")[1].trim();

                int scoreValue = Integer.parseInt(scorePart.split("/")[0]);

                results.add(new Result(userName, scoreValue));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading leaderboard!");
        }

        // Sort highest score first
        results.sort((a, b) -> b.score - a.score);

        // Display Top 5
        for (int i = 0; i < Math.min(5, results.size()); i++) {
            Result r = results.get(i);
            System.out.println((i + 1) + ". " + r.name + " - " + r.score);
        }

        sc.close();
    }
}
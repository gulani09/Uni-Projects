import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		
	   BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

	   System.out.println("\nEnter the player name: ");
	   String name = scanner.nextLine();

	   // User enter the age and display the age varification
	   System.out.println("\nEnter the " + name + "'s age: ");
	   int age = scanner.nextInt();

	   if (age < 18) {
		   System.out.println("Age should be 18+..You can not play the game!!!");
	   } else {
	        int totalWins = 0;
	        int totalLosses = 0;

	        while (true)
	        {
	            int winCount = 0;
	            int loseCount = 0;

	            for (int i = 0; i < 100; i++)
	            {
	            	System.out.println("How much balance you have?");
	            	int balance = scanner.nextInt();
	            	System.out.println("\nThe minimum wages is : > 0 and maximum wages is <= balance");
	            	System.out.println("\nPlease enter the bet value : ");
	            	int betValue = scanner.nextInt();
	            	if (betValue > 0 && betValue <= balance) {
	            		int limit = 0;
		            	Player player = new Player(name, balance);
		                player.setLimit(limit);
		                int bet = betValue;

		                System.out.println(String.format("Start Game %d: ", i + 1));
		                System.out.println(String.format("%s starts with balance %d, limit %d", 
		                		player.getName(), player.getBalance(), player.getLimit()));

		                int turn = 0;
		                while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200)
		                {
		                    Dice d1 = new Dice();
		                    Dice d2 = new Dice();
		                    Dice d3 = new Dice();

		                    Game game = new Game(d1, d2, d3);
		                    List<DiceValue> cdv = game.getDiceValues();
		                    turn++;                    
		                	DiceValue pick = DiceValue.getRandom();
		                   
		                	System.out.printf("Turn %d: %s bet %d on %s\n",
		                			turn, player.getName(), bet, pick); 
		                	
		                	int winnings = game.playRound(player, pick, bet);
		                    cdv = game.getDiceValues();
		                    
		                    System.out.printf("Rolled %s, %s, %s\n",
		                    		cdv.get(0), cdv.get(1), cdv.get(2));
		                    
		                    if (winnings > 0) {
			                    System.out.printf("%s won %d, balance now %d\n\n",
			                    		player.getName(), winnings, player.getBalance());
			                	winCount++; 
		                    }
		                    else {
			                    System.out.printf("%s lost, balance now %d\n\n",
			                    		player.getName(), player.getBalance());
			                	loseCount++;
		                    }
		                    
		                } //while

		                System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i + 1));
		                System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
	            	} else {
	            		System.out.println("\nBet value should be under limitation!!!");
	            	}
	                System.out.println("Do you want to continue the play? (Y/N)");
	                String isContinue = console.readLine();
	                if (isContinue.equalsIgnoreCase("N")) {
	                	break;
	                }
	                
	            } //for
	            
	            System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", winCount, loseCount, (float) winCount/(winCount+loseCount)));
	            totalWins += winCount;
	            totalLosses += loseCount;
	            
	            System.out.println("Please enter 'q' to quit the program");
	            String ans = console.readLine();
	            if (ans.equals("q")) break;
	        } //while true
	        System.out.println(String.format("Overall win rate = %.1f%%", (float)(totalWins * 100) / (totalWins + totalLosses)));
	   }
	}

}

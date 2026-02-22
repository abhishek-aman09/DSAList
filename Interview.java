import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Interview {

    /*
    Build a platform for Online election:
    There can be N Candidates  and M Voters. Each voter can place maximum up to 3 votes to different candidates
    Find the top 3 winning candidate based on the number of votes received by them after every vote is casted. In-case of a draw on any position, return all the candidates on that position
    
    Example: 
    Candidates - C1 , C2, C3, C4, C5
    Voters - V1, V2, V3, V4, V5, V6, V7, V8, V9, V10
    
    Vote Casting:
    V1 - C1, C4, C5
    Winners : 
        1'st Pos:  [C1, C4, C5] with 1 Vote
        2nd Pos: [], 3rd Pos: []
    
    V2 - C2, C4,
    Winners : 
        1'st Pos:  [C4] with 2 Votes
        2nd Pos: [C1, C2, C5] with 1 Vote
        3rd Pos: []
    
    V3 - C4, C5, C3
    Winners:  
        1'st Pos:  [C4] with 3 Votes
        2nd Pos: [C5] with 2 Votes
        3rd Pos: [C1, C2, C3] with 1 Vote
    
    V4 - C4, C3
    Winners:  
        1'st Pos:  [C4] with 4 Votes
        2nd Pos: [C5, C3] with 2 Votes
        3rd Pos: [C1, C2] with 1 Vote
    
        voters = m * 3
        candidate = n
    
    
        Voter - voterId, array of votes
        Candidate - id, numOfVotes
        voteCounter(List<Voters> , List<Candidates>)
        leaderBoard(List<Candidates>)
    
    
        
    */

    private void voting(int n, int voters[][], int candidate[]) {

        PriorityQueue<CandidateWithVotes> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.votes, a.votes)
        );


        for (int votes[] : voters) {
            int round = 1;
            for (int v : votes) {
                candidate[v]++;
            }
            for (int ind = 1; ind <= n; ind++) {
                if (candidate[ind] > 0) {
                    pq.add(new CandidateWithVotes(ind, candidate[ind]));
                }
                
            }
            
            int counter = 3;
            int currVote = pq.peek().votes;

            System.out.println("Result after round " + round);
            round++;

            while (counter > 0 && !pq.isEmpty()) {

                while (!pq.isEmpty() && pq.peek().votes == currVote) {
                    CandidateWithVotes curr = pq.poll();
                    System.out.print(curr.candidate + " ");
                }
                System.out.println();

                if (!pq.isEmpty()) {
                    currVote = pq.peek().votes;
                }
                counter--;

            }
            
            pq.clear();
            
        }

    }

    class CandidateWithVotes {
        int candidate;
        int votes;

        CandidateWithVotes(int candidate, int votes) {
            this.candidate = candidate;
            this.votes = votes;
        }
    }


    public static void main(String[] args) {
        Interview obj = new Interview();

        int voters[][] = {
            {1,4,5},
                    {2, 4},
                            {4, 5, 3},
                                    {4, 3}
        };

        int n = 5;
        int candidate[] = new int[n + 1];


        obj.voting(n, voters, candidate);

    }
    
}

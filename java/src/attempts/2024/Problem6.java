/** 
    
    Tried to sort position of obstacles
    Then using modified binary search find closest obstacle and calculate distance
    
    It gets tricky since you have to calculate distinct cells (so if guard walks on the same cell twice it should be only counted once)

    For that I was thinking of using Line Sweep Algorithm
 */ 









//  package year_2024;

//  import utils.TerminalPrint;
//  import java.util.*;
 
//  import root.RootProblem;
 
//  public class Problem6 extends RootProblem{
     
//      private int problemNumber = 6;
//      int dir = 0;
//      int[][] rows;
//      int[][] cols;
     
//      protected void solve(Integer partNumber){
//          String[] input = readFileLineByLine(problemNumber, 0);
 
//          if(input == null || input.length == 0){
//              TerminalPrint.printWrongProblemInputMsg();
//              return;
//          }
 
//          switch (partNumber) {
//              case 1:
//                  partOne(input);
//                  break;
//              case 2:
//                  partTwo(input);
//                  break;
//              default:
//                  return;
//          }
//      }
 
//      private void partOne(String[] input){
//          Long sum = 0l;
 
//          List<int[]> coords = new ArrayList<>();
//          int[] start = new int[2];
//          int row = 0;
//          // System.out.println(input.length + " dfdf " + input[0].length()); 
//          for(String line: input){
//              for(int i = 0; i < line.length(); i++){
//                  if(line.charAt(i) == '#'){
//                      int[] coord = {row, i};
//                      coords.add(coord);
//                  }else if(line.charAt(i) == '^'){
//                      start[0] = row;
//                      start[1] = i;
//                  }
//              }
//              row++;
//          }
         
//          rows = new int[coords.size()][2];
//          cols = new int[coords.size()][2];
         
//          for(int i = 0; i < coords.size(); i++){
//              rows[i] = coords.get(i);
//              cols[i] = coords.get(i);
//          }
 
//          Arrays.sort(rows, (a, b) -> a[0] - b[0]);
//          Arrays.sort(cols, (a, b) -> a[1] - b[1]);
         
//          // System.out.println("WTF???"); 
//          // System.out.println(start[0]); 
//          // System.out.println(modBinarySearch(start, 1, 0)); 
//          // System.out.println("HELLO?"); 
 
//          boolean done = false;
//          int nextSpot;
//          int[] curPos = start;
//          int[] nextPos = start;
//          int steps = 1;
//          Set<int[]> visited = new HashSet<>();
//          visited.add(nextPos);
//          while(!done){
//              int dim = dir % 2 == 0 ? 0 : 1;
//              int way = (dir == 0 || dir == 3) ? 0 : 1;
//              // System.out.println("CUR POS: " + Arrays.toString(curPos)); 
//              nextSpot = modBinarySearch(curPos, dim, way);
 
//              if(nextSpot < 0){
//                  System.out.println("ALREADY?"); 
//                  steps += way == 0 ? curPos[dim] : (input.length - 1) - curPos[dim];
//                  // System.out.println("NEW steps: " + (way == 0 ? curPos[dim] : (input.length - 1) - curPos[dim])); 
//                  done = true;
//                  break;
//              }
             
//              nextSpot = way == 0 ? nextSpot + 1 : nextSpot - 1;
//              turn();
//              nextPos[dim] = nextSpot;
//              // visited.add(nextPos);
//              System.out.println(Arrays.toString(nextPos)); 
 
//              // if()
//              // if(visited.contains(nextPos)){
//              //     curPos[dim] = nextSpot;
//              //     continue;
//              // }else{
//              //     visited.add(nextPos);
//              // }
//              // System.out.println("NEW POS: " + Arrays.toString(curPos)); 
//              curPos[dim] = nextSpot;
//              if(nextSpot < 0){
//                  steps += way == 0 ? curPos[dim] : (input.length - 1) - curPos[dim];
//                  // System.out.println("NEW steps: " + (way == 0 ? curPos[dim] : (input.length - 1) - curPos[dim])); 
//                  done = true;
//              }else{
//                  steps += way == 0 ? curPos[dim] - nextSpot : nextSpot - curPos[dim];
//                  // System.out.println("NEW steps %: " + (way == 0 ? curPos[dim] - nextSpot : nextSpot - curPos[dim])); 
//              }
//              // curPos[dim] = nextSpot;
//              // break;
//          }
 
//          TerminalPrint.printAnswerMsg(problemNumber, 1, steps);
//      }
     
//      private void turn(){
//          dir = (dir + 1) % 4;
//      }
 
//      private int[] findNextCoord(int[] pos){
//          if(dir == 0){
             
//          }
//          return null;
//      }
 
//      private int modBinarySearch(int[] pos, int dim, int way){
 
//          int[][] fullList = dim == 0 ? rows : cols;
//          List<int[]> list = new ArrayList<>();
//          for(int i = 0; i < fullList.length; i++){
//              if(fullList[i][(dim + 1) % 2] == pos[(dim + 1) % 2]){
//                  list.add(fullList[i]);
//              }
//          }
 
//          for(int[] r: list){
//              // System.out.println(Arrays.toString(r)); 
//          }
 
//          if(way == 0){
//              if(pos[dim] < list.get(0)[dim]){
//                  return -1;
//              }else{
//                  if(list.size() == 1){
//                      return list.get(0)[dim];
//                  }
//              }
//          }else{
//              if(pos[dim] > list.get(list.size() - 1)[dim]){
//                  return -1;
//              }else{
//                  if(list.size() == 1){
//                      return list.get(list.size() - 1)[dim];
//                  }
//              }
//          }
 
//          int start = 0, end = list.size() - 1;
//          int mid = -1;
//          int target = pos[dim];
//          int closest = -1;
//          int count = 0;
//          while(start <= end){
             
//              // if(count > 10)
//              //     break;
//              // count++;
//              // System.out.println(start + "___" + end); 
//              mid = (end + start) / 2;
//              if(target + 1 > list.get(mid)[dim]){
//                  closest = mid;
//                  start = mid + 1;
//              }else{
//                  end = mid - 1;
//              }
//          }
 
//          return way == 0? list.get(closest)[dim] : list.get(mid)[dim];
//      }
 
//      private void partTwo(String[] input){
//          Long sum = 0l;
//          for(String line: input){
//          }
//          TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
//      }
//  }
 





/**\
 * For part #2:
 * Tried to get 4th point for every 3 connected points (that doesn't work lol)
 *      int left = 0;
        int right = 2;
        int pointer = left;
        int x = 0;
        int y = 0;

        new_list.remove(new_list.size() - 1);

        System.out.println(new_list.size()); 

        for(int[] ss: new_list){
            System.out.println(Arrays.toString(ss)); 
        }

        while(right < new_list.size()){
            pointer = left;
            x = new_list.get(pointer)[0];
            y = new_list.get(pointer)[1];
            while(pointer < right){
                pointer++;
                x ^= new_list.get(pointer)[0];
                y ^= new_list.get(pointer)[1];
            }
            left++;
            right++;
            System.out.println(x + ":" + y); 
            visited.add(x + ":" + y);
            x = 0;
            y = 0;
        }
 */
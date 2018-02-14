package javaPrograms;

public class AddingDiagnols {

	public static void main(String[] args) {
		int arr[][] = { {2,7,9},{3,6,1},{7,4,2} };
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				System.out.printf(arr[i][j]+" ");
						
			}
			System.out.println("\n");
			
		}
		int y=0;
		for (int i=0;i<3;i++){
		int x = arr[i][i];
			 y = x + y;
			
			
		}
		System.out.println(y);
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				System.out.printf(arr[i][j]+" ");
				if (i == j){
					arr[1][1] = y;
				}
						
			}
			System.out.println("\n");
			
		}
		for (int i=0;i<3;i++){
			int x = arr[i][arr.length-1];
			System.out.println("x1 = "+x);
			    x = x + arr[i][i];
			    System.out.println("x2 = "+x);
			    x = arr[arr.length-1][i]+x;
			    System.out.println("x3 = "+x);
				 y = x;
				
				
			}
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				System.out.printf(arr[i][j]+" ");
				if (i == j){
					arr[2][0] = y;
				}
						
			}
			System.out.println("\n");
			
		}

	}

}

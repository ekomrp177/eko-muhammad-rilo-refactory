#include <iostream>
using namespace std;

int findNearestFibonacci(int sum){
	if (sum < 0) {
		cout<<sum<<" is wrong, because Fibonacci should be positive number"<<endl;
		return sum;
	}else if(sum == 0 || sum == 1 || sum == 2 || sum == 3) return 0;
    else {
    	int t1 = 0, t2 = 1, n = sum;
		int nextTerm = t1 + t2;

    	do{
        	t1 = t2;
        	t2 = nextTerm;
    		if (sum < (t1+t2)){
    			return (t1+t2)-sum;
			}else if(sum == (t1+t2)) return 0;
        	nextTerm = t1 + t2;
    	}while(nextTerm <= n);
	}
}

int main(){
	int arr[] = {15, 1, 3};
	
	int arrSize = sizeof(arr)/sizeof(arr[0]);
	int sum = 0;
	for (int i = 0; i<arrSize; i++) {
		sum += arr[i];
	}
	
	cout<<"Result : "<<findNearestFibonacci(sum)<<endl;
	
	cout<<endl;system("pause");
	return 0;
}

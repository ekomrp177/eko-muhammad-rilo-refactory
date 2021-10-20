#include <iostream>
using namespace std;

int main() {
	int n;
	
	cout<<"Input n number : ";cin>>n;
	cout<<endl;
	for(int i = 1; i <= n; i++) {
		if (i == 1) cout<<"[\"";
		if (i % 3 == 0 && i % 5 == 0) cout<<"FizzBuzz";
		else if (i % 3 == 0) cout<<"Fizz";
		else if (i % 5 == 0) cout<<"Buzz";
		else cout<<i;
		if (i == n) cout<<"\"]";
		else if(i < n) cout<<"\", \"";
	}
	
	cout<<endl;system("pause");
	return 0;
}

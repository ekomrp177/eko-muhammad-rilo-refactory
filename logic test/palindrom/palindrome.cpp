#include <iostream>
using namespace std;

string checkPalindrome(string value){
	int len = value.length();
	for (int i = 0; i < len/2; i++) {
		if (value.at(i) != value.at(len - 1 - i)) {
          return "("+value+") is not a palindrome";
      }
	}
	return "("+value+") is a Palindrom";
}

int main() {
	string value;
	cout<<"Input Value : ";getline(cin, value);
	cout<<checkPalindrome(value)<<endl;
	
	cout<<endl;system("pause");
	return 0;
}

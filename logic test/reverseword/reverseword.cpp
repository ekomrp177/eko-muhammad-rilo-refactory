#include <iostream>
using namespace std;

int getSpace(string value, int index){
	for(int i = index; i<value.length(); i++){
		if(isspace(value.at(i))) {
			return i;
		}
	}
	return value.length();
}

string reverse(string value) {
	int index = 0;
	string temp = value;
	int checkIndex = value.length();
	do {
		checkIndex = getSpace(value, index);
		int tempIndex = index;
		int indexSpace = getSpace(value, index)-1;
		index = indexSpace+2;
		for(int j = 0; j <= indexSpace-tempIndex; j++) {
			temp.at(j+tempIndex) = (isupper(value.at(j+tempIndex))) ? toupper(value.at(indexSpace-j)) : tolower(value.at(indexSpace-j));
		}
	}while(checkIndex != value.length());
	return temp;
}

int main(){
	string value;
	
	cout<<"Input value    : ";getline(cin, value);
	cout<<endl;
	cout<<"Before Reverse : "<<value<<endl;
	cout<<"After Reverse  : "<<reverse(value);
	
	cout<<endl;system("pause");
	return 0;
}

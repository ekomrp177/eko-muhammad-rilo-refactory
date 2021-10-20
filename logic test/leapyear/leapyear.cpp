#include <iostream>
#include <stdlib.h>
using namespace std;

void checkLeapYear(int firstYear, int lastYear){
	bool notFirst = false;
	for (int temp = firstYear; temp <= lastYear; temp++){
		if (temp % 4 == 0) {
        	if (temp % 100 == 0) {
            	if (temp % 400 == 0) {
        			if (temp != firstYear) cout<<", ";
        			cout<<temp;
        			notFirst = true;
				}
        	}
        	else {
        		if (notFirst) cout<<", ";
        		cout<<temp;
        			notFirst = true;
			}
    	}
	}
}

int main(){
	int firstYear, lastYear;
	
    cout<<"Enter first year: ";cin>>firstYear;
    cout<<"Enter last year : ";cin>>lastYear;
    cout<<endl;
    checkLeapYear(firstYear, lastYear);

	cout<<endl;system("pause");
    return 0;
}

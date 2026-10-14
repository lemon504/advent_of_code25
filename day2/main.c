#include <string.h>
#include <stdio.h>
#include <stdlib.h>

int part_one(char* number){
    int len = strlen(number);
    if (len % 2 != 0)
    {
        return 0;
    }
    
    int middle = len/2;
    for (int i = 0; i < middle; i++)
    {
        if (number[i] != number[i+middle])
        {
            return 0;
        }
    }
    return 1;
}

int part_two(char* number){
    int len = strlen(number);
    int middle = len/2;

    //schrittweite
    for (int i = 1; i < middle; i++)
    {
        for (int j = 0; j < len; j+=i)
        {
            if (number[i] != number[i+middle]) break;
        }
        return 1;
    }
    
}

int main(int argc, char const *argv[])
{
    //open and read from file
    FILE *fd = fopen("input.txt", "r");
    char buf[600];
    char d[] = ",-";
    fgets(buf, 600, fd);
    fclose(fd);

    long long input[73];

    //Source: https://www.c-howto.de/tutorial/strings-zeichenketten/string-funktionen/string-zerteilen/
    char *ptr = strtok(buf, d);
    int i = 0;
    while (ptr != NULL)
    {
        //get current number and store
        char* endptr;
        long long x = strtoll(ptr, &endptr, 10);
        input[i] = x;

        //next number
        ptr = strtok(NULL, d);
        i = i+1;
    }
    
    long long res = 0;
    for (int i = 0; i < 73; i = i+2)
    {
        long long lower_bound = input[i];
        long long upper_bound = input[i+1];

        for (long long i = lower_bound; i <= upper_bound; i++)
        {
            //Source: https://stackoverflow.com/questions/8257714/how-can-i-convert-an-int-to-a-string-in-c
            char number[20];
            sprintf(number, "%lld", i);

            res = (part_one(number)) ? res + i : res;
        }
    }

    printf("%lld\n", res);
}
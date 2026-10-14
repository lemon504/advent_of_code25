#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    FILE * fd = fopen("input.txt", "r");
    int input[200][110];

    //loop over lines and convert into int in input matrix
    int x = 0;
    char tmp_buf[110];
    while (fgets(tmp_buf, 110, fd) != NULL)
    {
        int i = 0;
        while (tmp_buf[i] != '\0')
        {
            input[x][i] = (int)(tmp_buf[i] - '0');
            i++;
        }
        x++;
    }
    
    int res = 0;

    for (int i = 0; i < 200; i++)
    {
        //get first highest digit
        int highest = 0;
        int highest_ptr;
        for (int j = 0; j < 99; j++)
        {
            if (input[i][j] > highest)
            {
                highest = input[i][j];
                highest_ptr = j;
            }
        }
        highest *= 10;

        //start from highest digit and get the second
        int sec_highest = 0;
        for (int x = highest_ptr+1; x < 100; x++)
        {
            if (input[i][x] > sec_highest)
            {
                sec_highest = input[i][x];
            }
        }
        highest += sec_highest;
        
        res += highest;
    }
    printf("%d\n", res);

    return 0;
}
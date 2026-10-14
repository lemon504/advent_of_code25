#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    //read from file
    FILE *fd = fopen("input.txt", "r");
    char input[142][142];
    char line_buffer[160];
    int j = 0;
    while (fgets(line_buffer, 160, fd) != NULL)
    {
        for (int i = 0; line_buffer[i] != '\0'; i++) input[j][i] = line_buffer[i];
        input[j][141] = '\0';
        j++;
    }

    //parse trough each cell
    int res = 0;
    for (int i = 0; i < 142; i++)
    {
        for (int j = 0; j < 141; j++)
        {
            //start
            if (input[i][j] == 'S')
            {
                input[i+1][j] = '|';
            }
            //splitter and beam above -> split
            if (i != 0 && input[i][j] == '^' && input[i-1][j] == '|')
            {
                res++;
                input[i][j+1] = '|';
                input[i][j-1] = '|';
            }
            //continue beam from above
            else if (i != 0 && input[i-1][j] == '|')
            {
                input[i][j] = '|';
            }
        }
    }

    //print result
    for (int i = 0; i < 142; i++) printf("%s\n", input[i]);
    printf("%d\n", res);
    return 0;
}
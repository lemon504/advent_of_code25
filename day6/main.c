#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    FILE *fd = fopen("input.txt", "r");
    long long line1[1000], line2[1000], line3[1000], line4[1000];
    char operators[1000];

    char line_buffer[3800];
    int i = 1;

    //read from file
    while (fgets(line_buffer, 3800, fd) != NULL)
    {
        char *tmp = strtok(line_buffer, "    ");
        char * endptr;
        if (i < 5)
        {
            switch (i)
                {
                case 1:
                    line1[0] = strtoll(tmp, &endptr, 10);
                    break;
                case 2:
                    line2[0] = strtoll(tmp, &endptr, 10);
                    break;
                case 3:
                    line3[0] = strtoll(tmp, &endptr, 10);
                    break;
                case 4:
                    line4[0] = strtoll(tmp, &endptr, 10);
                    break;
            }
        }
        else
        {
            operators[0] = tmp[0];
        }
        int j = 1;
        while ((tmp = strtok(NULL, "    ")) != NULL)
        {
            if (strcmp(tmp, "\n") == 0) break;
            
            if (i < 5)
            {
                switch (i)
                {
                case 1:
                    line1[j] = strtoll(tmp, &endptr, 10);
                    break;
                case 2:
                    line2[j] = strtoll(tmp, &endptr, 10);
                    break;
                case 3:
                    line3[j] = strtoll(tmp, &endptr, 10);
                    break;
                case 4:
                    line4[j] = strtoll(tmp, &endptr, 10);
                    break;
                }
            }
            else
            {
                operators[j] = tmp[0];
            }
            j++;
        }
        i++;
    }

    long long res = 0;
    for (int i = 0; i < 1000; i++)
    {
        //multiply or adding lines
        res += (operators[i] == '*') ? line1[i] * line2[i] * line3[i] * line4[i] : line1[i] + line2[i] + line3[i] + line4[i];
    }
    printf("%lld\n", res);
    return 0;
}
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//+1 if id in range from range matrix
int is_in_range(long long id, long long ranges[190][2])
{
    for (long long i = 0; i < 190; i++)
    {
        if (ranges[i][0] <= id && id <= ranges[i][1])
        {
            return 1;
        }
    }
    return 0;
}

int main(int argc, char const *argv[])
{
    FILE *fd = fopen("input.txt", "r");

    long long range_matrix[190][2];
    long long ids[1000];
    int ranges = 1;
    char line_buffer[40];

    int i = 0;
    int j = 0;
    while (fgets(line_buffer, 40, fd) != NULL)
    {
        if (strcmp("\n", line_buffer) == 0)
        {
            ranges = 0;
            continue;
        }

        if (ranges)
        {
            char *s1 = strtok(line_buffer, "-");
            char *s2 = strtok(NULL, "\n");

            char* endptr;
            range_matrix[i][0] = strtoll(s1, &endptr, 10);
            range_matrix[i][1] = strtoll(s2, &endptr, 10);
            i++;
        }
        else
        {
            char* endptr;
            ids[j] = strtoll(line_buffer, &endptr, 10);
            j++;
        }
    }

    int res = 0;
    for (int i = 0; i < 1000; i++) res += is_in_range(ids[i], range_matrix);
    printf("%d\n", res);
}
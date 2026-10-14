#include <stdlib.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{

    //read from file
    char* FILENAME = "input.txt";
    FILE* fd = fopen(FILENAME, "r");
    char input[4499][6];
    int i = 0;
    while (fgets(input[i], sizeof(input[i]), fd) != NULL && i < 4499) i++;
    fclose(fd);

    int cur_state = 50;
    int turns = 0;

    for (int i = 0; i < 4499; i++)
    {
        char* tmp = input[i];

        //get direction
        int dir = (tmp[0] == 'R') ? 1 : -1;

        //copy number in seperate buffer cause its const
        char buf[4];
        int y = 1;
        while (tmp[y] != '\0')
        {
            buf[y-1] = tmp[y];
            y++;
        }
        buf[y-1] = '\0';
        
        //x is now step
        char* endptr;
        int x = (int) strtol(buf, &endptr, 10);

        //±245 is just ±45 with 2 extra turns
        int extra_turns = x / 100;
        turns+= extra_turns;
        x %= 100;

        switch (dir)
        {
        case -1:
            //left

            //go over 0
            if (x > cur_state)
            {
                turns += cur_state != 0;
                cur_state = 100 - (x-cur_state);
            }
            else
            {
                cur_state -= x;
            }
            turns += cur_state == 0;
            break;
        default:
            //right
            cur_state += x;
            int overflow = cur_state / 100;
            cur_state %= 100;
            turns += overflow;
            break;
        }
    }
    printf("%d\n", turns);
}
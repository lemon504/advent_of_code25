#include <stdio.h>

int get_paper_rolls(int x, int y, char matrix[139][145], int right_bound, int lower_bound){
    int res = 0;

    //upper lane
    if (x - 1 >= 0 && matrix[x-1][y] == '@') res ++;
    if (x - 1 >= 0 && y - 1 >= 0 && matrix[x-1][y-1] == '@') res ++;
    if (x - 1 >= 0 && y + 1 < right_bound && matrix[x-1][y+1] == '@') res ++;
    
    //lower lane
    if (x + 1 < lower_bound && matrix[x+1][y] == '@') res ++;
    if (x + 1 < lower_bound && y - 1 >= 0 && matrix[x+1][y-1] == '@') res ++;
    if (x + 1 < lower_bound && y + 1 < right_bound && matrix[x+1][y+1] == '@') res ++;

    //left and right
    if (y - 1 >= 0 && matrix[x][y-1] == '@') res ++;
    if (y + 1 < right_bound && matrix[x][y+1] == '@') res ++;

    return res;
}

int main(int argc, char const *argv[])
{
    FILE *fd = fopen("input.txt", "r");
    char matrix[139][145];
    int i = 0;

    while (fgets(matrix[i++], 145, fd) != NULL);

    int res = 0;

    while (1)
    {
        int temp_res = 0;
        for (int i = 0; i < 139; i++)
        {
            for (int j = 0; j < 139; j++)
            {
                if (matrix[i][j] == '@' && get_paper_rolls(i, j, matrix, 139, 139) < 4) {
                    matrix[i][j] = '.';
                    temp_res++;
                }
            }
        }
        printf("eliminated %d papers\n");
        if (temp_res == 0) break;
        res += temp_res;
    }

    printf("%d\n", res);
}
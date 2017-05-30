using System;
using System.Collections.Generic;
using System.IO;
class Solution {
    static void Main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution */  
        var q = Int32.Parse(Console.ReadLine());
        
        while (q-- > 0) {
            var a = Console.ReadLine();
            var b = Console.ReadLine();
            
            int[,] res = new int[a.Length + 1, b.Length + 1];
            
            for (int i = 0; i < a.Length; i++)
                for (int j = 0; j < b.Length; j++)
                    res[i, j] = Int32.MaxValue;

            for (int i = 0; i < a.Length; i++)
                for (int j = 0; j < b.Length; j++) {
                    
                    var prev1 = Math.Max(0, i - 1);
                    var prev2 = Math.Max(0, j - 1);
                

                    if (i == 0 && j == 0) res[i, j] = 0; 
                    else
                    if (a[i] == b[j]) res[i, j] = res[prev1, prev2];
                    else if(a[i].ToString().ToUpper() == b[j].ToString()) {

                        if (res[prev1, j] < Int32.MaxValue) 
                            res[i, j] = Math.Min((res[prev1, j] + 1), (res[prev1, prev2] + 1));
                        else if (res[prev1, prev2] < Int32.MaxValue)
                            res[i, j] = res[prev1, prev2] + 1;
                        else res[i, j] = Int32.MaxValue;
                    }
                    else if (a[i].ToString().ToUpper() != a[i].ToString()) {
                        res[i, j] = res[prev1, j];
                    }
                    else
                        res[i, j] = Int32.MaxValue;

                }
            
            Console.WriteLine((res[a.Length - 1, b.Length - 1] < Int32.MaxValue) ? "YES" : "NO");
        }
    }
}
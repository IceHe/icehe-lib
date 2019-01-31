<?php

class Solution {
    function numJewelsInStones($J, $S) {
        $map = [];
        $jLen = strlen($J);
        for ($i = 0; $i < $jLen; ++$i) {
            $map[$J[$i]] = true;
        }

        $cnt = 0;
        $sLen = strlen($S);
        for ($i = 0; $i < $sLen; ++$i) {
            if ($map[$S[$i]] ?? false) {
                ++$cnt;
            }
        }
        return $cnt;
    }
}

echo $J = "aA";
echo "\n";
echo $S = "aAAbbbb";
echo "\n";
echo (new Solution())->numJewelsInStones($J, $S);
echo "\n";

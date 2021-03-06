<?php

// 引用 bug

$ary = [1, 2, 3];

foreach ($ary as &$v) {
}

foreach ($ary as $v) {
}

var_dump($ary);

// Why?
// foreach 不销毁最后一个循环的元素！
// - 第一个 foreach 没销毁 $v，
//     而它对 $ary 对最后一个元素的引用并无解除！
//     （应该使用额外的 `unset($v);` 操作来解除引用）
// - 第二个循环，循环到倒数第二个元素，
//     最后一个元素已经变成倒数第二个元素的值，
//     然后最后一个元素自己对自己赋值
// - 所以最后结果是 [1, 2, 2]

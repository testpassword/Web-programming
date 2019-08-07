<?php

$startTime = microtime(true);
$x = (float) $_POST["x"];
$y = (float) $_POST["y"];
$r = (float) $_POST["r"];
$timezone = $_POST["timezone"];
date_default_timezone_set($timezone);
$answer = array(
    "coordsStatus" => checkCoordinates($x, $y, $r),
    "currentTime" => date("H : i"),
    "benchmarkTime" => (microtime(true) - $startTime)
);
echo json_encode($answer);

function checkCoordinates(float $x, float $y, float $r) {
    if ((($x >= -$r/2) && ($x <= 0) && ($y >= 0) && ($y <= $r/2)) || (($x >= 0) && ($x <= $r) && ($y >= $r) && ($y >= -$r))) return true;
    else return false;
}

//TODO svg отдельным файлом
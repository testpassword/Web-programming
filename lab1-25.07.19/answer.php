<?php

$startTime = microtime(true);
$x = (int) $_POST["x"];
$y = (int) $_POST["y"];
$r = (int) $_POST["r"];
date_default_timezone_set("Europe/Moscow");
$answer = array(
    "coordsStatus" => checkCoordinates($x, $y, $r),
    "currentTime" => date("H:m"),
    "benchmarkTime" => (microtime(true) - $startTime)
);
echo json_encode($answer);

function checkCoordinates(int $x, int $y, int $r) {
    if ((($x >= -$r/2) && ($x <= 0) && ($y >= 0) && ($y <= $r/2)) || (($x >= 0) && ($x <= $r) && ($y >= $r) && ($y >= -$r))) return true;
    else return false;
}

//TODO таймзона клиента
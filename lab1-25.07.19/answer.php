<?php

@session_start();
if (!isset($_SESSION["tableRows"])) {
    $_SESSION["tableRows"] = array();
    date_default_timezone_set($_POST["timezone"]);
}
$x = (float) $_POST["x"];
$y = (float) $_POST["y"];
$r = (float) $_POST["r"];
$coordsStatus = checkCoordinates($x, $y, $r);
$currentTime = date("H : i : s");
$benchmarkTime = microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"];
array_push($_SESSION["tableRows"], "<tr>
    <td>$x</td>
    <td>$y</td>
    <td>$r</td>
    <td>$coordsStatus</td>
    <td>$currentTime</td>
    <td>$benchmarkTime</td>
    </tr>");
echo "<table id='outputTable'>
        <tr>
            <th>x</th>
            <th>y</th>
            <th>r</th>
            <th>Точка входит в ОДЗ</th>
            <th>Текущее время</th>
            <th>Время работы скрипта</th>
        </tr>";
foreach ($_SESSION["tableRows"] as $tableRow) echo $tableRow;
echo "</table>";

function checkCoordinates(float $x, float $y, float $r) {
    if ((($x >= -$r/2) && ($x <= 0) && ($y >= 0) && ($y <= $r/2)) ||
        (($x >= 0) && ($x <= $r) && ($y >= $r) && ($y >= -$r)) ||
        (($x**2 + $y**2) <= (($r**2)/2) && ($x >= 0) && ($y >= 0))) return "да";
    else return "нет";
}
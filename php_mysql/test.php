<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 上午10:27
 */
$url = 'http://localhost/androidProject/like.php';
$data = array('tag'=>'delete','uuid' => '55489ada07c119.20995428','p_id' => 16);

// use key 'http' even if you send the request to https://...
$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
        'method'  => 'POST',
        'content' => http_build_query($data),
    ),
);
$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);
echo $result;

?>

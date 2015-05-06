<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 上午10:27
 */
$url = 'http://localhost/androidProject/profile.php';
$data = array('tag'=>'profile','unique_id' => '55486c64e26206.31580395');

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

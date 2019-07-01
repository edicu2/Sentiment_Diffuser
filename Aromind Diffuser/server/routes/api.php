<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('register', 'UserController@register');
Route::post('login', 'UserController@login');
Route::get('profile', 'UserController@getAuthenticatedUser');

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('emotions', 'EmotionsController@emotionSearch');

Route::resource('products', 'ProductsController');

Route::resource('articles', 'ArticlesController');


Route::get('/day', 'DateController@day');
Route::get('/week', 'DateController@week');
Route::get('/month', 'DateController@month');
Route::get('/year', 'DateController@year');
Route::get('/recommend/{commend}', 'DateController@recommend');
Route::resource('customcard','CustomCardController');
Route::get('/woman', 'DateController@woman');
Route::get('/man', 'DateController@man');

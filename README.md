# TrendAnalyzer
2019年度 栃木高校 SS課題研究  
「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する ～」  
プログラム群  
  
<img src="https://raw.githubusercontent.com/NomakiItsuru/TrendAnalyzer/master/flow_chart.png" />
  
# Creating Debug Environment
0. JDK8をインストールする。
1. 論文注釈[[10]](https://drive.google.com/drive/folders/0B4y35FiV1wh7fjQ5SkJETEJEYzlqcUY4WUlpZmR4dDlJMWI5ZUlXN2xZN2s2b0pqT3hMbTQ)からMeCab（mecab-0.996.exe）をUTF-8でインストールする。  
2. 論文注釈[[5]](https://mana.bi/wiki.cgi?page=%B7%C1%C2%D6%C1%C7%B2%F2%C0%CF%B4%EFMeCab%23p7)で頒布されているNEologd辞書（2019/8/31版）をダウンロードし、MeCabに適用する。  
3. このリポジトリをgit cloneでクローンし、Mavenでpackageビルドする。（メインクラスはdev/itsu/trendanalyzer/TrendAnalyzer.kt）  
4. jarと同じディレクトリにsettings.propertiesを作り、以下の記述をする。ただしTwitterのトークン類は用意していただく必要がある。  
```settings.properties
OAuthConsumerKey=[OAUTH_CONSUMER_KEY]
OAuthConsumerSecret=[OAUTH_CONSUMER_SECRET]
OAuthAccessToken=[OAUTH_ACCESS_TOKEN]
OAuthAccessSecret=[OAUTH_ACCESS_SECRET]
```  
5. 論文注釈[[2]](http://www.lr.pi.titech.ac.jp/~takamura/pndic_ja.html)から単語感情極性対応表（日本語）をダウンロードし、jarと同じディレクトリに配置する。
6. java -jar [jar名] -create-dictionary:[単語感情極性対応表（日本語）のパス]を実行。

# Program Arguments
実行時に括弧（[]）は必要ありません。  
  
- -create-dictionary:[file_name]  
file_nameで指定した指定フォーマット（単語:読み:品詞名:感情極性値）の辞書ファイルからデータベースを生成します。  
  
- -authorize-twitter  
ビルド済みプログラムに配置したsettings.propertiesに記述したTwitter認証トークン類を使ってTwitterにログインします。  
  
- -start-analyze:[trend_count]:[tweet_count]  
trend_countで指定したトレンド数、tweet_countで指定したツイート数の解析を行います。  
  
- -test-mecab:[text]  
textで指定した文章をMeCabで解析し、実行結果を出力します。  
  
- -test-analyze:[text]  
textで指定した文章の感情極性値評価を行い、その結果を出力します。  
  
- -output:[file_path]
-start_analyzeの実行結果であるcsvファイルの保存先を指定します。  
ファイル名に```_###_```を使用することでこの部分に日時が入ります。（デフォルト：```output__###_.csv```）  
  
# Output File Formats
UTF-8のcsv形式で出力されます。  
- Index トレンド番号  
- Name トレンド名  
- Evaluation 感情評価値  
- Time 解析日時
## Example of output file
output_201910300926.csv
```output_201910300926.csv
Index,Name,Evaluation,TweetCount,Time
0,ペイペイドーム,-0.3275407305120491,100,2019/10/30 09:24:40
1,Monster,-0.16837849980476188,100,2019/10/30 09:24:45
2,遺体発見,-0.5145398918246793,100,2019/10/30 09:24:50
3,決闘罪,-0.47010096798146617,42,2019/10/30 09:25:02
4,#pop_a,-0.27478933171807357,100,2019/10/30 09:25:06
5,FGOメンテ,-0.3968474211320344,100,2019/10/30 09:25:13
6,ソフトB本拠地,-0.1214018551166669,100,2019/10/30 09:25:19
7,クリヘム,-0.5656185111256936,100,2019/10/30 09:25:23
8,メンテ延長,-0.4445218939642857,100,2019/10/30 09:25:29
9,プロ野球ソフトバンクの本拠地,-0.3270232240333336,100,2019/10/30 09:25:37
10,愛染くん,-0.35474420695139264,100,2019/10/30 09:25:43
11,#CDJ1920,-0.34635208679151214,100,2019/10/30 09:25:51
12,#片目選手権,-0.1360188217666666,100,2019/10/30 09:25:58
13,#にじさんじ魔女集会,-0.29874142255666675,100,2019/10/30 09:26:01
14,#すずはライブ,-0.3210617552586574,100,2019/10/30 09:26:06
15,#秘められしあなたのチート能力,-0.5399578141506104,100,2019/10/30 09:26:11
16,#屍人荘ハロウィン,-0.446175702061252,100,2019/10/30 09:26:19
17,#ローカル路線バス乗り継ぎの旅,-0.4114253751999844,100,2019/10/30 09:26:29
18,#鹿児島ユナイテッドFC,-0.31759489167533694,100,2019/10/30 09:26:34
19,#それに教えたら終わり,-0.5191876194824422,100,2019/10/30 09:26:43
```
  


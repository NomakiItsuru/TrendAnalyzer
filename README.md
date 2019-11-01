# TrendAnalyzer
2019年度 栃木高校 SS課題研究  「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する ～」 プログラム群  
  
# Create Debug Environment
1. 論文注釈[10]からMeCab（mecab-0.996.exe）をUTF-8でインストールする。  
2. 論文注釈[5]で頒布されているNEologd辞書（2019/8/31版）をダウンロードし、MeCabに適用する。  
3. このリポジトリをgit cloneでクローンし、Mavenでpackageビルドする。（メインクラスはdev/itsu/trendanalyzer/TrendAnalyzer.kt）  
4. jarと同じディレクトリにsettings.propertiesを作り、以下の記述をする。ただしトークン類は用意していただく必要がある。  
```settings.properties
OAuthConsumerKey=[OAUTH_CONSUMER_KEY]
OAuthConsumerSecret=[OAUTH_CONSUMER_SECRET]
OAuthAccessToken=[OAUTH_ACCESS_TOKEN]
OAuthAccessSecret=[OAUTH_ACCESS_SECRET]
```  
5. 論文注釈[2]から単語感情極性対応表（日本語）をダウンロードし、jarと同じディレクトリに配置する。
6. java -jar [jar名] -create-dictionary:[語感情極性対応表（日本語）のパス]を実行する。

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
  


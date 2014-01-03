amount_crawled = """SELECT lastcrawled,count(fullurl) from urls WHERE lastcrawled >= current_date - integer '%s'
        GROUP BY lastcrawled ORDER BY lastcrawled"""
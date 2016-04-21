jQuery(function() {

	var facts = [
		"The stock market is an everyday term we use to talk about a place where stocks and bonds are traded – meaning bought and sold. For many people, that is the first thing that comes to mind for investing. The goal is to buy the stock, hold it for a time, and then sell the stock for more than you paid for it. ",
		"How long do you hang on to stock? Investors who hold stock for 15 years or more usually succeed in the market. Stocks are long-term investments. But there are no guarantees.",
		"In the stock market, prices rise and fall every day. When you invest in the stock market, you are hoping that over the years, the stock will become much more valuable than the price you paid for it."
	];

	var random_index = Math.floor(Math.random()*facts.length);

	$("#student-fact").text(facts[random_index]);

})
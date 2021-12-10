Feature: Login

# " " double quotes are used as inline parameters for the actual gluecode methods. It will be passed in as arguments

Scenario: Invalid password, valid username (negative test)
	Given I am at the login page
	When I type in a username of "Rdant" 
	But I type in a password of "jlkjljsdf"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
Scenario: Invalid password, invalid username (negative test)
	Given I am at the login page
	When I type in a username of "rafel_d"
	And I type in a password of "Billy"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
Scenario: Valid password, invalid username (negative test)
	Given I am at the login page
	When I type in a username of "adsfsldjfkljlkjlkjkljlk"
	And I type in a password of "Iam1337"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
# Scenario v. Scenario Outline
# Scenario: its a single grouping of steps that will be executed once sequentially
# Scenario Outline: a template of steps that will be executed multiple times sequentially
	
Scenario Outline: Successful employee login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the employee homepage
	
	Examples:
		| username | password |
		| "MasterChief117" | "Iam1337"  |
		| "Kratos_war" | "password"    |

		
Scenario Outline: Successful Finance manager login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the Finance manager homepage
	
	Examples:
		| username | password |
		| "Rdant" | "pass123" |		
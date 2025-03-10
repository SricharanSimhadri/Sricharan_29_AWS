package com.jsfeb.assetmanagementsystem.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.jsfeb.assetmanagementsystem.dto.AssetInfoBeans;
import com.jsfeb.assetmanagementsystem.dto.RequestInfoBeans;
import com.jsfeb.assetmanagementsystem.dto.UserInfoBeans;
import com.jsfeb.assetmanagementsystem.exception.AMException;
import com.jsfeb.assetmanagementsystem.services.AdminServices;
import com.jsfeb.assetmanagementsystem.services.AdminServicesImple;
import com.jsfeb.assetmanagementsystem.services.UserServices;
import com.jsfeb.assetmanagementsystem.services.UserServicesImple;
import com.jsfeb.assetmanagementsystem.validations.Validation;

public class UserController {

	public static Scanner scan = new Scanner(System.in);

	public static final LoginController control = new LoginController();
	public static final UserInfoBeans userInfo = new UserInfoBeans();
	public static final AssetInfoBeans assetsInfo = new AssetInfoBeans();
	public static final UserServices userServices = new UserServicesImple();
	public static final AdminServices adminServices = new AdminServicesImple();
	public static final Validation validation = new Validation();
	

	public static int checkChoice() {
		boolean flag = false;
		int select = 0;
		do {
			try {
				select = scan.nextInt();
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Invalid Choice, It Should Contain Only Digits");
				scan.next();
			}
		} while (!flag);
		return select;
	}

	public static int checkId() {
		boolean flag = false;
		int id = 0;
		do {
			try {
				id = scan.nextInt();
				validation.validateId(id);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Id should Contain Only Digits");
				scan.next();
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return id;
	}

	public static String checkName() {
		String name = null;
		boolean flag = false;
		do {
			try {
				name = scan.next();
				validation.validateName(name);
				flag = true;
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return name;
	}

	public static String checkEmailId() {
		String emailId = null;
		boolean flag = false;
		do {
			try {
				emailId = scan.next();
				validation.validateEmail(emailId);
				flag = true;
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return emailId;
	}

	public static String checkPassword() {
		String password = null;
		boolean flag = false;
		do {
			try {
				password = scan.next();
				validation.validatePassword(password);
				flag = true;
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return password;
	}

	public static String checkCategory() {
		String category = null;
		boolean flag = false;
		do {
			try {
				category = scan.next();
				validation.validateName(category);
				flag = true;
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return category;
	}

	public static int checkQuantity() {
		boolean flag = false;
		int quantity = 0;
		do {
			try {
				quantity = scan.nextInt();
				validation.validateQuantity(quantity);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Quantity should Contain Only Digits");
				scan.next();
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return quantity;
	}

	public static String checkPrice() {
		boolean flag = false;
		String price = null;
		do {
			try {
				price = scan.next();
				validation.validatePrice(price);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Price should be in numbers only");
				scan.next();
			} catch (AMException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return price;
	}

	public static void userController() {

		int id = 0;

		int choice = 0;

		do {

			System.out.println("1 to view details");
			System.out.println("2 to send requests to add assets");
			System.out.println("3 to delete assets");
			System.out.println("4 to get all your assets");
			System.out.println("5 to logout");

			choice = checkChoice();

			switch (choice) {

			case 1:

				System.out.println("Your details are:");

				List<UserInfoBeans> records = userServices.viewDetails();

				for (UserInfoBeans details : records) {

					System.out.println("Name     =====  " + details.getName());
					System.out.println("Id       =====  " + details.getUserId());
					System.out.println("Email    =====  " + details.getEmail());
					System.out.println("Password =====  " + details.getPassword());

				}

				break;

			case 2:

				System.out.println("Add your required assets");

				System.out.println("Enter user id");
				id = checkId();
				userInfo.setUserId(id);

				System.out.println("Enter asset name");
				String name = checkName();
				assetsInfo.setAssetName(name);

				System.out.println("Enter asset category");
				String category = checkCategory();
				assetsInfo.setCategory(category);

				System.out.println("Enter asset quantity");
				int quantity = checkQuantity();
				assetsInfo.setQuantity(quantity);

				System.out.println("Enter asset price");
				double price = Double.parseDouble(checkPrice());
				assetsInfo.setPrice(price);

				try {

					boolean status = userServices.requestAsset(id, name, quantity);

					if (status) {

						adminServices.acceptRequest(id, name);
						
						System.out.println("request accepted");

					}

				} catch (AMException e) {

					System.err.println(e.getMessage());

				}
				break;

			case 3:

				System.out.println("Enter name of the asset");

				name = checkName();

				try {

					boolean status = userServices.removeAsset(name);

					if (status) {
						System.out.println("removed asset successfully");
					}

				} catch (AMException e) {

					System.err.println(e.getMessage());

				}

				break;

			case 4:

				System.out.println("Enter your id");
				id = checkId();
				
				System.out.println("Your assets are:");

				List<RequestInfoBeans> record = userServices.viewAllAssets(id);

				for (RequestInfoBeans details : record) {

					System.out.println("Asset name     ======= " + details.getAssetName());
					System.out.println("Asset quantity ======= " + details.getQuantity());

				}

				break;

			case 5:

				System.out.println("Logged out successfullyy");
				break;

			default:

				System.err.println("Invalid details");
				break;

			}

		} while (choice != 5);

		LoginController.loginController();

		scan.close();
	}

}

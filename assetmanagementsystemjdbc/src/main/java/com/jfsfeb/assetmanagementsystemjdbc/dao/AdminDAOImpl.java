package com.jfsfeb.assetmanagementsystemjdbc.dao;

public class AdminDAOImple implements AdminDAO {
	DBConnector dbConnector = new DBConnector();

	
	 public boolean addAsset(AssetInfoBeans asset) {
		 try (Connection conn = dbConnector.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("addAsset"));) {
			 pstmt.setInt(1, asset.getAssetId());
				pstmt.setString(2, asset.getAssetName());
				pstmt.setString(3, asset.getCompanyName());
				pstmt.setString(4, asset.getCategory());
				pstmt.setDouble(5, asset.getPrice());
				pstmt.setInt(6, asset.getQuantity());
				pstmt.setString(7, asset.getStatus());


				pstmt.executeUpdate();

			} catch (Exception e) {
				throw new AMSException("Can't add asset, because asset id is already present");
			}
			return true;
				}


		public boolean deleteAsset(int id) {
			
			try (Connection conn = dbConnector.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("deleteAsset"));) {
				pstmt.setInt(1, id);
				int result = pstmt.executeUpdate();
				if (result != 0) {
					return true;
				}

			} catch (Exception e) {
				throw new AMSException(e.getMessage());

			}
			throw new AMSException("Can't delete , No asset found");
		
		}

		
		public ArrayList<AssetInfoBeans> searchAsset(int id) {
			ArrayList<AssetInfoBeans> searchAsset = new ArrayList<AssetInfoBeans>();
			try (Connection conn = dbConnector.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("searchAsset"));) {
				pstmt.setInt(1, id);
				try (ResultSet resultSet = pstmt.executeQuery();) {
					if (resultSet.next()) {
						AssetInfoBeans assetinfobeans = new AssetInfoBeans();
						assetinfobeans.setAssetId(resultSet.getInt("AssetId"));
//						assetinfobeans.setBookName(resultSet.getString(""));
//						assetinfobeans.setAuthor(resultSet.getString(""));
//						assetinfobeans.setPublisher(resultSet.getString(""));

						return searchAsset;
					}
				}
			} catch (Exception e) {
				throw new AMSException(e.getMessage());
			}
			throw new AMSException("Assets not found");

			}


		public List<AssetInfoBeans> getAllAssets() {
		
			List<AssetInfoBeans> allAssets = new ArrayList<AssetInfoBeans>();
			
			try (Connection connection = dbConnector.getConnection();
					PreparedStatement stmt = connection.prepareStatement(dbConnector.getQuery("getAllLoansQuery"))) {
				 ResultSet resultset = stmt.executeQuery();
				
				while (resultset.next()) {
					AssetInfoBeans bean = new AssetInfoBeans();
					bean.setAssetId(resultset.getInt("AssetId"));
					bean.setAssetName(resultset.getString("AssetName"));
					bean.setCategory(resultset.getString("Category"));
					bean.setCompanyName(resultset.getString("CompanyName"));
					bean.setPrice(resultset.getDouble("price"));
					allAssets.add(AssetInfoBeans);
				}
				return allAssets;
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			throw new AMSException("Loan id already exists..");
			}

		
		public List<RequestInfoBeans> requestDetails() {
			
			List<RequestInfoBeans> requests = new ArrayList<RequestInfoBeans>();
			try (Connection connection = dbConnector.getConnection();
					PreparedStatement stmt = connection.prepareStatement(dbConnector.getQuery("getAllLoansQuery"))) {
				 ResultSet resultset = stmt.executeQuery();
				 while (resultset.next()) {
						RequestInfoBeans bean = new RequestInfoBeans();
						bean.setUserId(resultset.getInt("AssetId"));
						bean.setUserName(resultset.getString("AssetName"));
						bean.setAssetId(resultset.getInt("Category"));
						bean.setAssetName(resultset.getString("CompanyName"));
						bean.setStatus(resultset.getString("price"));
						requests.add(RequestInfoBeans);
			}
			
			return requests;
		}

		
		public boolean acceptRequest(int id, String name) {
			
			RequestInfoBeans requestInfo = new RequestInfoBeans();
			
			for (RequestInfoBeans accept : Repository.requestBeans) {
			
				if (id == accept.getUserId()) {
				
					for (AssetInfoBeans assetBean : Repository.assetBeans) {
					
						if (assetBean.getAssetName().contentEquals(name)) {
						
							requestInfo.setUserId(id);
							requestInfo.setAssetName(name);
							String status = "true";
							requestInfo.setStatus(status);
							Repository.requestBeans.add(requestInfo);
							return true;
						
						}
					
					}
				
				}
			
			}

			throw new AMSException("Accept is not valid");
		}

package in.jaxer.api.request;

/**
 *
 * @author Shakir Ansari
 */
public abstract class AbstractMultipartTask
{
//	private static final Logger logger = Logger.getLogger(AbstractMultipartTask.class);
//
//	protected int uniqueMediaCount = 0;
//	protected int duplicateMediaCount = 0;
//
//	private RequestResponseObject requestResponseObject = null;
//	private Set<String> invalidTagList = null;
//
//	public abstract void doMultipartTask(Connection connection) throws Exception;
//
//	public void processAbstractMultipartTask(Connection connection) throws Exception
//	{
//		preMultipartTask(connection);
//
//		doMultipartTask(connection);
//
//		postMultipartTask(connection);
//
//		if (Validator.isNotEmpty(invalidTagList))
//		{
//			setParameter("invalidTagList", AppUtils.toJsonString(invalidTagList));
//		}
//
//		setParameter("taskResponseCode", "101");
//		setParameter("uniqueMediaCount", String.valueOf(uniqueMediaCount));
//		setParameter("duplicateMediaCount", String.valueOf(duplicateMediaCount));
//		setParameter("totalMediaCount", String.valueOf(uniqueMediaCount + duplicateMediaCount));
//	}
//
//	protected void preMultipartTask(Connection connection)
//	{
//	}
//
//	protected void postMultipartTask(Connection connection)
//	{
//	}

//	public Timestamp getTimeStamp()
//	{
//		return requestResponseObject.getTimeStamp();
//	}
//
//	public RequestResponseObject getRequestResponseObject()
//	{
//		return requestResponseObject;
//	}
//
//	public void setRequestResponseObject(RequestResponseObject requestResponseObject)
//	{
//		this.requestResponseObject = requestResponseObject;
//	}
//
//	public void setParameter(String key, Object value)
//	{
//		getRequestResponseObject().setParameter(key, value);
//	}
//
//	public String getParameter(String key)
//	{
//		return getRequestResponseObject().getParameter(key);
//	}
//
//	public User getRequestUser()
//	{
//		return getRequestResponseObject().getRequestUser();
//	}
//
//	protected List<Part> getPartList() throws Exception
//	{
//		List<Part> partList = null;
//		Collection<Part> parts = this.requestResponseObject.getHttpServletRequest().getParts();
//
//		if (Validator.isEmpty(parts))
//		{
//			return partList;
//		}
//
//		for (Part part : parts)
//		{
//			if (Validator.isEmpty(part.getSubmittedFileName()))
//			{
//				continue;
//			}
//
//			if (Validator.isEmpty(partList))
//			{
//				partList = new ArrayList<>();
//			}
//			partList.add(part);
//		}
//
//		return partList;
//	}
//
//	@Deprecated
//	private String getPartChecksum(Part part) throws Exception
//	{
//		InputStream inputStream = new BufferedInputStream(part.getInputStream());
//		inputStream.mark((int) part.getSize());
//		return AppUtils.hash.getFileChecksum(inputStream);
//	}
//
//	@Deprecated
//	private Dimension getImageDimension(Part part) throws Exception
//	{
//		InputStream inputStream = new BufferedInputStream(part.getInputStream());
//
//		Dimension dimension = Utilities.getImageDimension(inputStream);
//		if (dimension == null)
//		{
//			dimension = new Dimension(0, 0);
//		}
//
//		return dimension;
//	}
//
//	protected MediaFile saveMediaFile(Connection connection, MultiPart multiPart, FileManager fileManager, long lastModified, int isProfileImage) throws Exception
//	{
//		logger.debug("lastModified: [" + lastModified + "], multiPart: [" + multiPart + "], fileManager: [" + fileManager + "]");
//
//		File compressedFile = multiPart.getCompressedFile();
//		logger.debug("compressedFile: [" + compressedFile + "]");
//
//		MediaFile mediaFile = new MediaFile();
//		mediaFile.setFileManagerId(fileManager.getId());
//		mediaFile.setFileName(multiPart.getNewFileName());
//		mediaFile.setExtension(AppUtils.getExtension(multiPart.getOriginalFileName()));
//		mediaFile.setEncryptedLength(compressedFile.length());
//		mediaFile.setWidth(0);
//		mediaFile.setHeight(0);
//		mediaFile.setResolution(0);
//		mediaFile.setChecksum(multiPart.getOriginalChecksum());
//		mediaFile.setOriginalFileName(multiPart.getOriginalFileName());
//		mediaFile.setOriginalLength(multiPart.getOriginalFile().length());
//		mediaFile.setOriginalLastModified(lastModified);
//		mediaFile.setSalt(multiPart.getMediaSalt());
//		mediaFile.setIsPrivate(1);
//		mediaFile.setIsProfileMedia(isProfileImage);
//		mediaFile.setActiveStatus(AppConstants.ActiveStatus.ACTIVE);
//		mediaFile.setCreated(getTimeStamp());
//		mediaFile.setCreator(getRequestUser().getId());
//
//		DbHandler.persist(connection, mediaFile);
//		logger.debug("After persist - mediaFile: [" + mediaFile + "]");
//
//		fileManager.setFileCount(fileManager.getFileCount() + 1);
//		DbHandler.merge(connection, fileManager);
//		logger.debug("After merge - fileManager: [" + fileManager + "]");
//
//		//Finally copy compressedFile to targetlocation
//		File lastFile = new File(AppUtils.getNewFileLocation(fileManager, AppConstants.MediaFileType.IMAGE), multiPart.getNewFileName());
//		logger.debug("lastFile: [" + lastFile + "]");
//		Files.copy(compressedFile, lastFile);
//
//		return mediaFile;
//	}
//
//	protected void addInvalidTag(String tagTitle)
//	{
//		if (invalidTagList == null)
//		{
//			invalidTagList = new HashSet<>();
//		}
//		invalidTagList.add(tagTitle);
//	}
//
//	private void mapTags(Connection connection, int objectId, String objectType, List<String> hashtagTitleList) throws Exception
//	{
//		logger.debug("objectId: [" + objectId + "], objectType: [" + objectType + "], hashtagTitleList: [" + hashtagTitleList + "]");
//		if (Validator.isEmpty(hashtagTitleList))
//		{
//			return;
//		}
//
//		Timestamp timestamp = getTimeStamp();
//
//		for (String hashtagTitle : hashtagTitleList)
//		{
//			if (!AppUtils.Validation.isValidTagTitle(hashtagTitle))
//			{
//				logger.warn("Invalid hashtagTitle: [" + hashtagTitle + "]");
//
//				addInvalidTag(hashtagTitle);
//				continue;
//			}
//
//			Hashtag hashtag = DbOperations.getOrCreateHashtag(connection, getRequestUser().getId(), hashtagTitle);
//			logger.debug("hashtag: [" + hashtag + "]");
//			if (hashtag.getActiveStatus() != AppConstants.ActiveStatus.ACTIVE)
//			{
//				logger.warn("Inactive - hashtag: [" + hashtag + "]");
//				hashtag.setActiveStatus(AppConstants.ActiveStatus.ACTIVE);
//
//				DbOperations.merge(connection, hashtag);
//				logger.debug("After merge - hashtag: [" + hashtag + "]");
//			}
//
//			List<Parameter> parameterList = Arrays.asList(
//					new Parameter("tag_id", hashtag.getId(), true),
//					new Parameter("object_id", objectId, true),
//					new Parameter("object_type", objectType, true));
//
//			logger.debug("parameterList: [" + parameterList + "]");
//
//			List<HashtagRelation> mapHashtagList = DbOperations.findList(connection, HashtagRelation.class, parameterList);
//			logger.debug("mapHashtagList: [" + mapHashtagList + "]");
//			if (Validator.isEmpty(mapHashtagList))
//			{
//				HashtagRelation mapHashtag = new HashtagRelation();
//				mapHashtag.setHashtagId(hashtag.getId());
//				mapHashtag.setObjectId(objectId);
//				mapHashtag.setObjectType(objectType);
//				mapHashtag.setCreated(timestamp);
//
//				DbOperations.persist(connection, mapHashtag);
//				logger.debug("After persist - mapHashtag: [" + mapHashtag + "]");
//			}
//		}
//	}
//
//	protected void mapTags(Connection connection, MediaFile mediaFile, List<String> tagTitleList) throws Exception
//	{
//		mapTags(connection, mediaFile.getId(), AppConstants.Table.OBJECT_TYPE_MEDIA_FILE, tagTitleList);
//	}
//
//	protected void mapTags(Connection connection, MediaAlbum mediaAlbum, List<String> tagTitleList) throws Exception
//	{
//		mapTags(connection, mediaAlbum.getId(), AppConstants.Table.OBJECT_TYPE_MEDIA_ALBUM, tagTitleList);
//	}
}

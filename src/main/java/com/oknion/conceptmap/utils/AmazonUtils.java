package com.oknion.conceptmap.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.oknion.conceptmap.Model.Document;

public class AmazonUtils {

	private static final String SUFFIX = "/";

	public static final AmazonS3 CLIENT = new AmazonS3Client(getCredential());

	private static AWSCredentials getCredential() {
		AWSCredentials credentials = null;
		try {
			credentials = new BasicAWSCredentials("AKIAIBR6OFSHVOFZIXQA",
					"6KsGGcLlvHLCwfHidxkOVamfxliGG0jC9z+cFwUf");

		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. "
							+ "Please make sure that your credentials file is at the correct "
							+ "location (C:\\Users\\Oknion\\.aws\\credentials), and is in valid format.",
					e);
		}
		return credentials;
	}

	public static boolean upload2S3(Document document, String bucketName)
			throws IOException {

		System.out.println("Upload document to S3");

		AmazonS3 s3 = new AmazonS3Client(getCredential());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);

		String key = document.getS3KeyIdString();

		System.out.println("===========================================");
		System.out.println("Getting Started with Amazon S3");
		System.out.println("===========================================\n");

		try {

			/*
			 * Create a new S3 bucket - Amazon S3 bucket names are globally
			 * unique, so once a bucket name has been taken by any user, you
			 * can't create another bucket with that same name.
			 * 
			 * You can optionally specify a location for your bucket if you want
			 * to keep your data closer to your applications or users.
			 */

			/*
			 * List the buckets in your account
			 */
			System.out.println("Listing buckets");
			for (Bucket bucket : s3.listBuckets()) {
				System.out.println(" - " + bucket.getName());

			}
			System.out.println();

			/*
			 * Upload an object to your bucket - You can easily upload a file to
			 * S3, or upload directly an InputStream if you know the length of
			 * the data in the stream. You can also specify your own metadata
			 * when uploading to S3, which allows you set a variety of options
			 * like content-type and content-encoding, plus additional metadata
			 * specific to your applications.
			 */

			System.out.println("Uploading a new object to S3 from a file\n");
			s3.putObject(new PutObjectRequest(bucketName, key,
					byte2File(document)));

		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return true;
	}

	private static File byte2File(Document document) throws IOException {
		File file = new File("tempFile");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(document.getBytes());
		fos.flush();
		fos.close();
		return file;
	}

	public static boolean createBucket(AmazonS3 client, String bucketName) {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		client.setRegion(usWest2);
		try {

			client.createBucket(bucketName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static S3Object getS3Object(String bucket, String path) {
		AWSCredentials credentials = getCredential();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);
		System.out.println("===========================================");
		System.out.println("Getting Started with Amazon S3");
		System.out.println("===========================================\n");
		try {

			/*
			 * Download an object - When you download an object, you get all of
			 * the object's metadata and a stream from which to read the
			 * contents. It's important to read the contents of the stream as
			 * quickly as possibly since the data is streamed directly from
			 * Amazon S3 and your network connection will remain open until you
			 * read all the data or close the input stream.
			 * 
			 * GetObjectRequest also supports several other options, including
			 * conditional downloading of objects based on modification times,
			 * ETags, and selectively downloading a range of an object.
			 */
			System.out.println("Downloading an object");
			S3Object object = s3.getObject(new GetObjectRequest(bucket, path));
			System.out.println("Content-Type: "
					+ object.getObjectMetadata().getContentType());
			System.out.println("Content-Length:"
					+ object.getObjectMetadata().getContentLength());
			return object;
		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return null;
	}

	public static void s3Object2Document(Document document, String bucketName) {

		AWSCredentials credentials = getCredential();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);

		String key = document.getS3KeyIdString();

		System.out.println("===========================================");
		System.out.println("Getting Started with Amazon S3");
		System.out.println("===========================================\n");
		try {

			/*
			 * Download an object - When you download an object, you get all of
			 * the object's metadata and a stream from which to read the
			 * contents. It's important to read the contents of the stream as
			 * quickly as possibly since the data is streamed directly from
			 * Amazon S3 and your network connection will remain open until you
			 * read all the data or close the input stream.
			 * 
			 * GetObjectRequest also supports several other options, including
			 * conditional downloading of objects based on modification times,
			 * ETags, and selectively downloading a range of an object.
			 */
			System.out.println("Downloading an object");
			S3Object object = s3
					.getObject(new GetObjectRequest(bucketName, key));
			System.out.println("Content-Type: "
					+ object.getObjectMetadata().getContentType());

			OutputStream outputStream = null;
			InputStream inputStream = object.getObjectContent();
			try {

				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(new File("tempFile"));

				int read = 0;
				byte[] bytes = new byte[document.getLength()];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				document.setBytes(bytes);

				System.out.println("Done!");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

			/*
			 * List objects in your bucket by prefix - There are many options
			 * for listing the objects in your bucket. Keep in mind that buckets
			 * with many objects might truncate their results when listing their
			 * objects, so be sure to check if the returned object listing is
			 * truncated, and use the AmazonS3.listNextBatchOfObjects(...)
			 * operation to retrieve additional results.
			 */
			// System.out.println("Listing objects");
			// ObjectListing objectListing = s3
			// .listObjects(new ListObjectsRequest().withBucketName(
			// bucketName).withPrefix("My"));
			// for (S3ObjectSummary objectSummary : objectListing
			// .getObjectSummaries()) {
			// System.out.println(" - " + objectSummary.getKey() + "  "
			// + "(size = " + objectSummary.getSize() + ")");
			// }

		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

	}

	public static void createFolder(String bucketName, String folderName,
			AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();

		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);

		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	/**
	 * This method first deletes all the files in given folder and than the
	 * folder itself
	 */
	public static void deleteFile(String bucketName, String filePath,
			AmazonS3 client) {
		client.deleteObject(bucketName, filePath);
	}

	public static void deleteFolder(String bucketName, String folderName,
			AmazonS3 client) {
		List<S3ObjectSummary> fileList = client.listObjects(bucketName,
				folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(bucketName, file.getKey());
		}
		client.deleteObject(bucketName, folderName);
	}

	public static List<String> getListFol(String bucketName, String prefix,
			AmazonS3 client) {

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(bucketName).withDelimiter("/")
				.withPrefix(prefix);
		ObjectListing objectListing = client.listObjects(listObjectsRequest);
		;

		return objectListing.getCommonPrefixes();
	}

	@SuppressWarnings("null")
	public static List<String> getListFile(String bucketName, String prefix,
			AmazonS3 client) {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(bucketName).withDelimiter("/")
				.withPrefix(prefix);
		List<String> returnStrings = new ArrayList<String>();
		ObjectListing objectListing;
		do {
			objectListing = client.listObjects(listObjectsRequest);

			for (S3ObjectSummary objectSummary : objectListing
					.getObjectSummaries()) {

				returnStrings.add(objectSummary.getKey());

			}
			listObjectsRequest.setMarker(objectListing.getNextMarker());
		} while (objectListing.isTruncated());

		return returnStrings;
	}
}

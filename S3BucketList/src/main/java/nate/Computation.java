package nate;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import java.util.ArrayList;

public class Computation
{
	private String access;
	private String secret;
	String BucketName;
	String FilePrefix;
	String Delimiter = "/";
	ArrayList<String> contents = new ArrayList<String>();
	AmazonS3 s3;
	
	public Computation(String acc, String sec, String bucket, String prefix)
	{
		access = acc;
		secret = sec;
		BucketName = bucket;
		FilePrefix = prefix;
		
		AWSCredentials credentials = new BasicAWSCredentials(access, secret);
		s3 = new AmazonS3Client(credentials);
		
		contents=SearchBucket();
	}
	
	public ArrayList<String> SearchBucket()
	{
		ArrayList<String> BContents = new ArrayList<String>();
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(BucketName).withPrefix(FilePrefix).withDelimiter(Delimiter);
		ObjectListing objectListing = s3.listObjects(listObjectsRequest);
		for (String prefix : objectListing.getCommonPrefixes())
		{
			BContents.add(prefix);
		}
		return BContents;
	}
	
	public ArrayList<String> getContents()
	{
		return contents;
	}
}
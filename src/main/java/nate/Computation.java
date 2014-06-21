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
	ArrayList<String> TestContents = new ArrayList<String>();
	ArrayList<String> SubContents = new ArrayList<String>();
	AmazonS3 s3;
	
	public Computation(String acc, String sec, String bucket, String prefix)
	{
		access = acc;
		secret = sec;
		BucketName = bucket;
		FilePrefix = prefix;
		
		AWSCredentials credentials = new BasicAWSCredentials(access, secret);
		s3 = new AmazonS3Client(credentials);
		
		contents=SearchBucket(FilePrefix);
		for (int i = 0; i<contents.size(); ++i)
		{
			String NewPrefix = FilePrefix+Delimiter+contents.get(i);
			TestContents = SearchBucket(NewPrefix);
			if (TestContents != null)
			{
				SubContents.addAll(TestContents);
			}
			else
			{
				SubContents.add("No Sub Files or Directories");
			}
			SubContents.add("STOP");	
		}
	}
	
	public ArrayList<String> SearchBucket(String Prefix)
	{
		ArrayList<String> BContents = new ArrayList<String>();
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(BucketName).withPrefix(Prefix).withDelimiter(Delimiter);
		ObjectListing objectListing = s3.listObjects(listObjectsRequest);
		//return objectListing; something like this to get all of the sub files.
		for (String prefixed : objectListing.getCommonPrefixes())
		{
			BContents.add(prefixed);
		}
		return BContents;
	}
	
	public ArrayList<String> getContents()
	{
		ArrayList<String> FinalContents = new ArrayList<String>();
		FinalContents.add("Top Directory");
		FinalContents.addAll(contents);
		FinalContents.add("Sub Directories");
		FinalContents.addAll(SubContents);
		return FinalContents;
	}
}
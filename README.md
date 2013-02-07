# Elastic Search Client for Scala

## Features

 - Asynchronous interface
 - Based on Scala 2.10 Futures
 - Single dispatch method, useful for command patterns
 - Type-safe Request / Response pairs: magnet pattern
 
The signature of the dispatch method (simplified):
  
   	package  org.scalastuff.esclient
  	import org.elasticsearch.client.Client
  	
 	implicit class ESClient(client : Client) {
      def apply[Request, Response](request: Request): Future[Response]
    }

## Usage

	 libraryDependencies += "org.scalastuff" % "esclient" % "0.20.1"
	 
Note that the esclient versioning is aligned with the elastic search version. This is the first esclient release based on 
Elastic Search version 0.20.

## Sample

	import scala.concurrent.Await
	import scala.concurrent.Future
	import scala.concurrent.duration.DurationInt
	
	import org.elasticsearch.action.index.IndexRequest
	import org.elasticsearch.action.index.IndexResponse
	import org.elasticsearch.client.Client
	import org.scalastuff.esclient.ESClient
	import org.elasticsearch.node.NodeBuilder.nodeBuilder;
	
	object TestES extends App {
	
	  val client : Client = 
	    nodeBuilder.node.client
	  
	  val response : Future[IndexResponse] = 
	    client(new IndexRequest) 
	      
	  println("Document id: " + Await.result(response, 5 seconds).id)
	}

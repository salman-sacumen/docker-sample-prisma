resource "prismacloud_cloud_account" "aws_example" {
    aws {
        name = "myAwsAccountName"
        account_id = "accountIdHere"
        external_id = "eidHere"
        group_ids = [
        ]
        role_arn = "some:arn:here"
    }
}
